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

package com.hedera.block.simulator.mode;

import static java.util.Objects.requireNonNull;

import com.hedera.block.simulator.config.data.BlockStreamConfig;
import com.hedera.block.simulator.generator.BlockStreamManager;
import edu.umd.cs.findbugs.annotations.NonNull;

public class CombinedModeHandler implements SimulatorModeHandler {
    private final BlockStreamConfig blockStreamConfig;

    public CombinedModeHandler(@NonNull final BlockStreamConfig blockStreamConfig) {
        requireNonNull(blockStreamConfig);
        this.blockStreamConfig = blockStreamConfig;
    }

    /**
     * Starts the simulator and initiate streaming, depending on the working mode.
     */
    @Override
    public void start(@NonNull BlockStreamManager blockStreamManager) {
        throw new UnsupportedOperationException();
    }
}
