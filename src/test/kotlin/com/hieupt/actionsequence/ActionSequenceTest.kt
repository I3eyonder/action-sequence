package com.hieupt.actionsequence

import kotlinx.coroutines.*
import org.junit.Test
import java.time.LocalTime
import java.util.concurrent.Executors

/**
 * Created by HieuPT on 11/7/2022.
 */
class ActionSequenceTest {

    @Test
    fun test1() = runBlocking {
        val actionSequence = ActionSequence(this)
        actionSequence.addActions(
            object : Action {
                override suspend fun doAction() {
                    withContext(Dispatchers.Default) {
                        println("${LocalTime.now()}: Action 1 run in ${Thread.currentThread()}")
                    }
                }
            },
            object : Action {
                override suspend fun doAction() {
                    withContext(Executors.newSingleThreadExecutor().asCoroutineDispatcher()) {
                        println("${LocalTime.now()}: Action 2 run in ${Thread.currentThread()}")
                    }
                }
            }
        )
        delay(1000)
        actionSequence.addActions(
            object : Action {
                override suspend fun doAction() {
                    coroutineScope {
                        launch {
                            delay(3000)
                            println("${LocalTime.now()}: Action 3.1 run in ${Thread.currentThread()}")
                            delay(1000)
                        }
                        launch {
                            delay(500)
                            println("${LocalTime.now()}: Action 3.2 run in ${Thread.currentThread()}")
                        }
                    }
                }
            }
        )
        actionSequence.addActions(
            object : Action {
                override suspend fun doAction() {
                    println("${LocalTime.now()}: Action 4 run in ${Thread.currentThread()}")
                }
            }
        )
        actionSequence.addActionAtFrontQueue(
            object : Action {
                override val id: Int
                    get() = 5

                override suspend fun doAction() {
                    println("${LocalTime.now()}: Action 5 run in ${Thread.currentThread()}")
                    delay(1000)
                }
            }
        )
        actionSequence.addActionAtFrontQueue(
            object : Action {
                override suspend fun doAction() {
                    println("${LocalTime.now()}: Action 6 run in ${Thread.currentThread()}")
                }
            }
        )
        actionSequence.removeActions(5)
        delay(5000)
        actionSequence.stop()
    }

    @Test
    fun test2(): Unit = runBlocking {
        launchActions {
            addAll(
                listOf(
                    object : Action {
                        override suspend fun doAction() {
                            withContext(Dispatchers.Default) {
                                println("${LocalTime.now()}: Action 1 run in ${Thread.currentThread()}")
                            }
                        }
                    },
                    object : Action {
                        override suspend fun doAction() {
                            withContext(
                                Executors.newSingleThreadExecutor().asCoroutineDispatcher()
                            ) {
                                println("${LocalTime.now()}: Action 2 run in ${Thread.currentThread()}")
                            }
                        }
                    },
                    object : Action {
                        override suspend fun doAction() {
                            coroutineScope {
                                launch {
                                    delay(3000)
                                    println("${LocalTime.now()}: Action 3.1 run in ${Thread.currentThread()}")
                                    delay(1000)
                                }
                                launch {
                                    delay(500)
                                    println("${LocalTime.now()}: Action 3.2 run in ${Thread.currentThread()}")
                                }
                            }
                        }
                    },
                    object : Action {
                        override val id: Int
                            get() = 5

                        override suspend fun doAction() {
                            println("${LocalTime.now()}: Action 5 run in ${Thread.currentThread()}")
                            delay(2000)
                        }
                    },
                    object : Action {
                        override suspend fun doAction() {
                            println("${LocalTime.now()}: Action 4 run in ${Thread.currentThread()}")
                        }
                    },
                    object : Action {

                        override suspend fun doAction() {
                            delay(4000L)
                            println("${LocalTime.now()}: Action 6 run in ${Thread.currentThread()}")
                        }
                    }
                )
            )
        }
    }

    @Test
    fun test3(): Unit = runBlocking {
        listOf(
            object : Action {
                override suspend fun doAction() {
                    withContext(Dispatchers.Default) {
                        println("${LocalTime.now()}: Action 1 run in ${Thread.currentThread()}")
                    }
                }
            },
            object : Action {
                override suspend fun doAction() {
                    withContext(Executors.newSingleThreadExecutor().asCoroutineDispatcher()) {
                        println("${LocalTime.now()}: Action 2 run in ${Thread.currentThread()}")
                    }
                }
            },
            object : Action {
                override suspend fun doAction() {
                    coroutineScope {
                        launch {
                            delay(3000)
                            println("${LocalTime.now()}: Action 3.1 run in ${Thread.currentThread()}")
                            delay(1000)
                        }
                        launch {
                            delay(500)
                            println("${LocalTime.now()}: Action 3.2 run in ${Thread.currentThread()}")
                        }
                    }
                }
            },
            object : Action {
                override val id: Int
                    get() = 5

                override suspend fun doAction() {
                    println("${LocalTime.now()}: Action 5 run in ${Thread.currentThread()}")
                    delay(2000)
                }
            },
            object : Action {
                override suspend fun doAction() {
                    println("${LocalTime.now()}: Action 4 run in ${Thread.currentThread()}")
                }
            },
            object : Action {

                override suspend fun doAction() {
                    delay(4000L)
                    println("${LocalTime.now()}: Action 6 run in ${Thread.currentThread()}")
                }
            }
        ).launchIn(this)
    }

    @Test
    fun test4(): Unit = runBlocking {
        arrayOf(
            object : Action {
                override suspend fun doAction() {
                    withContext(Dispatchers.Default) {
                        println("${LocalTime.now()}: Action 1 run in ${Thread.currentThread()}")
                    }
                }
            },
            object : Action {
                override suspend fun doAction() {
                    withContext(Executors.newSingleThreadExecutor().asCoroutineDispatcher()) {
                        println("${LocalTime.now()}: Action 2 run in ${Thread.currentThread()}")
                    }
                }
            },
            object : Action {
                override suspend fun doAction() {
                    coroutineScope {
                        launch {
                            delay(3000)
                            println("${LocalTime.now()}: Action 3.1 run in ${Thread.currentThread()}")
                            delay(1000)
                        }
                        launch {
                            delay(500)
                            println("${LocalTime.now()}: Action 3.2 run in ${Thread.currentThread()}")
                        }
                    }
                }
            },
            object : Action {
                override val id: Int
                    get() = 5

                override suspend fun doAction() {
                    println("${LocalTime.now()}: Action 5 run in ${Thread.currentThread()}")
                    delay(2000)
                }
            },
            object : Action {
                override suspend fun doAction() {
                    println("${LocalTime.now()}: Action 4 run in ${Thread.currentThread()}")
                }
            },
            object : Action {

                override suspend fun doAction() {
                    delay(4000L)
                    println("${LocalTime.now()}: Action 6 run in ${Thread.currentThread()}")
                }
            }
        ).launchIn(this)
    }
}