package com.hieupt.actionsequence

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import java.util.concurrent.Executors
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 * Created by HieuPT on 11/6/2022.
 */
class ActionSequence(private val coroutineScope: CoroutineScope, startNow: Boolean = true) {

    private val actionQueue: ArrayDeque<Action> = ArrayDeque()
    private val actionDispatcher = Executors.newSingleThreadExecutor().asCoroutineDispatcher()

    private var actionJob: Job? = null

    init {
        if (startNow) {
            start()
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun start() {
        actionJob?.cancel("New job started")
        actionJob = coroutineScope.launch(actionDispatcher) {
            val receiveChannel = produce {
                while (isActive) {
                    actionQueue.removeFirstOrNull()?.let { action ->
                        send(action)
                    } ?: yield()
                }
            }
            receiveChannel.consumeEach { action ->
                action.doAction()
            }
        }
    }

    fun stop() {
        actionJob?.cancel()
    }

    fun addActions(vararg actions: Action) {
        actionQueue.addAll(actions)
    }

    fun addActionAtFrontQueue(vararg actions: Action) {
        actionQueue.addAll(0, actions.toList())
    }

    fun removeActions(vararg actions: Action) {
        actionQueue.removeAll(actions.toSet())
    }

    fun removeActions(vararg actionIds: Int) {
        actionQueue.removeAll {
            actionIds.contains(it.id)
        }
    }
}

fun CoroutineScope.launchActions(
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    actionsBuilder: MutableList<Action>.() -> Unit
): Job = launch(context, start) {
    val actions = mutableListOf<Action>()
    actionsBuilder(actions)
    actions.forEach { action ->
        action.doAction()
    }
}

fun List<Action>.launchIn(coroutineScope: CoroutineScope): Job = coroutineScope.launch {
    forEach { action ->
        action.doAction()
    }
}

fun Array<Action>.launchIn(coroutineScope: CoroutineScope): Job = toList().launchIn(coroutineScope)