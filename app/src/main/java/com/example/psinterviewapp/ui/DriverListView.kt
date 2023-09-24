package com.example.psinterviewapp.ui

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.psinterviewapp.R
import com.example.psinterviewapp.ui.theme.PsInterviewAppTheme

private const val EXPAND_ANIMATION_DURATION_MILLIS = 500
private const val EXPANSTION_TRANSITION_DURATION_MILLIS = 250

@Composable
fun Driver(viewModelFactory: ViewModelProvider.Factory) {
    val viewModel: DriverListViewModel = viewModel(factory = viewModelFactory)

    val state by viewModel.state.collectAsState()

    if (state.errorMessage != null) {
        Error(state.errorMessage!!)
    } else {
        DriverList(state, viewModel)
    }
}

@Composable
private fun Error(@StringRes errorMessage: Int) {
    Box(contentAlignment = Alignment.Center) {
        Text(
            text = stringResource(id = errorMessage),
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Red
        )
    }
}

@Composable
private fun DriverList(
    state: DriverListState,
    listExpansionUpdater: ListExpansionUpdater<DriverListItemModel>
) {
    PsInterviewAppTheme {
        LazyColumn(contentPadding = PaddingValues(bottom = 24.dp, top = 8.dp)) {
            items(items = state.driverList, itemContent = {
                ExpandableCard(driverItem = it, expanded = it.isExpanded, onCardArrowClick = {
                    listExpansionUpdater.expandCollapseListItem(it)
                })
            })
        }
    }
}

@Composable
fun ExpandableCard(
    driverItem: DriverListItemModel,
    onCardArrowClick: () -> Unit,
    expanded: Boolean,
) {
    val transitionState = remember {
        MutableTransitionState(expanded).apply {
            targetState = !expanded
        }
    }
    val transition = updateTransition(transitionState, label = "")
    val cardPaddingHorizontal by transition.animateDp({
        tween(durationMillis = EXPAND_ANIMATION_DURATION_MILLIS)
    }, label = "") { _ ->
        if (expanded) 48.dp else 24.dp
    }
    val cardElevation by transition.animateDp({
        tween(durationMillis = EXPAND_ANIMATION_DURATION_MILLIS)
    }, label = "") { _ ->
        if (expanded) 24.dp else 4.dp
    }
    val cardRoundedCorners by transition.animateDp({
        tween(
            durationMillis = EXPAND_ANIMATION_DURATION_MILLIS,
            easing = FastOutSlowInEasing
        )
    }, label = "") { _ ->
        if (expanded) 0.dp else 16.dp
    }
    val arrowRotationDegree by transition.animateFloat({
        tween(durationMillis = EXPAND_ANIMATION_DURATION_MILLIS)
    }, label = "") { _ ->
        if (expanded) -90f else 90f
    }

    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = cardElevation),
        shape = RoundedCornerShape(cardRoundedCorners),
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = cardPaddingHorizontal,
                vertical = 8.dp
            )
    ) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                CardArrow(
                    degrees = arrowRotationDegree,
                    onClick = onCardArrowClick
                )
                CardTitle(title = driverItem.driverName)
            }
            ExpandableContent(
                visible = expanded,
                initialVisibility = expanded,
                content = driverItem.deliveryAddress
            )
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ExpandableContent(
    visible: Boolean = true,
    initialVisibility: Boolean = false,
    content: String
) {
    val enterTransition = remember {
        expandVertically(
            expandFrom = Alignment.Top,
            animationSpec = tween(EXPANSTION_TRANSITION_DURATION_MILLIS)
        ) + fadeIn(
            initialAlpha = 0.3f,
            animationSpec = tween(EXPANSTION_TRANSITION_DURATION_MILLIS)
        )
    }
    val exitTransition = remember {
        shrinkVertically(
            // Expand from the top.
            shrinkTowards = Alignment.Top,
            animationSpec = tween(EXPANSTION_TRANSITION_DURATION_MILLIS)
        ) + fadeOut(
            // Fade in with the initial alpha of 0.3f.
            animationSpec = tween(EXPANSTION_TRANSITION_DURATION_MILLIS)
        )
    }
    AnimatedVisibility(
        visibleState = remember { MutableTransitionState(initialState = initialVisibility) }
        .apply { targetState = visible },
        modifier = Modifier,
        enter = enterTransition,
        exit = exitTransition
    ) {
        Box(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Spacer(modifier = Modifier.heightIn(25.dp))
            Text(
                text = content,
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun CardArrow(
    degrees: Float,
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick,
        content = {
            Icon(
                painter = painterResource(id = R.drawable.ic_dropdown_arrow),
                contentDescription = "Expandable Arrow",
                modifier = Modifier.rotate(degrees),
            )
        },
    )
}

@Composable
fun CardTitle(title: String) {
    Text(
        text = title,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        textAlign = TextAlign.Center,
        fontSize = 24.sp,
    )
}