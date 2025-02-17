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

package com.hedera.block.common.utils;

import edu.umd.cs.findbugs.annotations.NonNull;
import java.util.Objects;

/** A utility class that deals with logic related to Strings. */
public final class StringUtilities {
    /**
     * This method checks if a given {@link String} is blank, meaning if it is {@code null} or
     * contains only whitespaces as defined by {@link String#isBlank()}. If the given {@link String}
     * is not blank, then we return it, else we throw {@link IllegalArgumentException}.
     *
     * @param toCheck a {@link String} to be checked if is blank as defined above
     * @return the {@link String} to be checked if it is not blank as defined above
     * @throws IllegalArgumentException if the input {@link String} to be checked is blank
     */
    public static String requireNotBlank(@NonNull final String toCheck) {
        if (Objects.requireNonNull(toCheck).isBlank()) {
            throw new IllegalArgumentException("A String required to be non-blank is blank.");
        } else {
            return toCheck;
        }
    }

    private StringUtilities() {}
}
