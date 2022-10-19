/*
 * Copyright (C) 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.openclassrooms.realestatemanager.viewModels

import android.app.Application
import com.openclassrooms.realestatemanager.myRoomDatabase.RealstateRoomDatabase
import com.openclassrooms.realestatemanager.repository.AgentRepository
import com.openclassrooms.realestatemanager.repository.RealtyRepository
import com.openclassrooms.realestatemanager.repository.ShotRepository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class RealStateManagerApplication : Application() {
    // No need to cancel this scope as it'll be torn down with the process
    val applicationScope = CoroutineScope(SupervisorJob())

    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val database by lazy {
        RealstateRoomDatabase.getDatabase(this, applicationScope)
    }
    val repository by lazy {
        RealtyRepository(database.realtyDao())
    }
    val repository2 by lazy {
        AgentRepository(database.agentDao())
    }
    val repositoryShot by lazy {
        ShotRepository(database.shotDao())
    }
}
