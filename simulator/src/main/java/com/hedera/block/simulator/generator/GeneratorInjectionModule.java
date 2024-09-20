/*
 * Copyright (C) 2024 Hedera Hashgraph, LLC
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

package com.hedera.block.simulator.generator;

import com.hedera.block.simulator.config.data.BlockStreamConfig;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

/** The module used to inject the block stream manager. */
@Module
public interface GeneratorInjectionModule {

    /**
     * Provides the block stream manager based on the configuration, either
     * BlockAsFileBlockStreamManager or BlockAsDirBlockStreamManager. by default, it will be
     * BlockAsFileBlockStreamManager.
     *
     * @param config the block stream configuration
     * @return the block stream manager
     */
    @Singleton
    @Provides
    static BlockStreamManager providesBlockStreamManager(BlockStreamConfig config) {

        if ("BlockAsDirBlockStreamManager".equalsIgnoreCase(config.managerImplementation())) {
            return new BlockAsDirBlockStreamManager(config);
        }

        return new BlockAsFileBlockStreamManager(config);
    }
}
