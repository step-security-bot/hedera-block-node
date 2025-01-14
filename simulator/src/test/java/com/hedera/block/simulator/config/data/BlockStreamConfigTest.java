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

package com.hedera.block.simulator.config.data;

import static org.junit.jupiter.api.Assertions.*;

import com.hedera.block.simulator.config.types.GenerationMode;
import com.hedera.block.simulator.config.types.StreamingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.Test;

class BlockStreamConfigTest {

    private final int delayBetweenBlockItems = 1_500_000;
    private final String blockStreamManagerImplementation = "BlockAsFileBlockStreamManager";
    private final int maxBlockItemsToStream = 10_000;
    private final int paddedLength = 36;
    private final String fileExtension = ".blk";
    private final StreamingMode streamingMode = StreamingMode.CONSTANT_RATE;
    private final int millisPerBlock = 1000;

    private String getAbsoluteFolder(String relativePath) {
        return Paths.get(relativePath).toAbsolutePath().toString();
    }

    @Test
    void testValidAbsolutePath() {
        // Setup valid folder path and generation mode
        String gzRootFolder = "src/main/resources/block-0.0.3/";
        String folderRootPath = getAbsoluteFolder(gzRootFolder);
        GenerationMode generationMode = GenerationMode.DIR;

        // Assume the folder exists
        Path path = Paths.get(folderRootPath);
        assertTrue(Files.exists(path), "The folder must exist for this test.");

        // No exception should be thrown
        BlockStreamConfig config =
                new BlockStreamConfig(
                        generationMode,
                        folderRootPath,
                        delayBetweenBlockItems,
                        blockStreamManagerImplementation,
                        maxBlockItemsToStream,
                        paddedLength,
                        fileExtension,
                        streamingMode,
                        millisPerBlock);

        assertEquals(folderRootPath, config.folderRootPath());
        assertEquals(GenerationMode.DIR, config.generationMode());
    }

    @Test
    void testEmptyFolderRootPath() {
        // Setup empty folder root path and generation mode
        String folderRootPath = "";
        GenerationMode generationMode = GenerationMode.DIR;

        // No exception should be thrown, and the default folder should be used
        BlockStreamConfig config =
                new BlockStreamConfig(
                        generationMode,
                        folderRootPath,
                        delayBetweenBlockItems,
                        blockStreamManagerImplementation,
                        maxBlockItemsToStream,
                        paddedLength,
                        fileExtension,
                        streamingMode,
                        millisPerBlock);

        // Verify that the path is set to the default
        Path expectedPath = Paths.get("src/main/resources/block-0.0.3/").toAbsolutePath();
        assertEquals(expectedPath.toString(), config.folderRootPath());
        assertEquals(GenerationMode.DIR, config.generationMode());
    }

    @Test
    void testRelativeFolderPathThrowsException() {
        // Setup a relative folder path and generation mode
        String relativeFolderPath = "relative/path/to/blocks";
        GenerationMode generationMode = GenerationMode.DIR;

        // An exception should be thrown because the path is not absolute
        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () ->
                                new BlockStreamConfig(
                                        generationMode,
                                        relativeFolderPath,
                                        delayBetweenBlockItems,
                                        blockStreamManagerImplementation,
                                        maxBlockItemsToStream,
                                        paddedLength,
                                        fileExtension,
                                        streamingMode,
                                        millisPerBlock));

        // Verify the exception message
        assertEquals(relativeFolderPath + " Root path must be absolute", exception.getMessage());
    }

    @Test
    void testNonExistentFolderThrowsException() {
        // Setup a non-existent folder path and generation mode
        String folderRootPath = "/non/existent/path/to/blocks";
        GenerationMode generationMode = GenerationMode.DIR;

        // Mock Files.notExists to return true
        Path path = Paths.get(folderRootPath);
        assertTrue(Files.notExists(path), "The folder must not exist for this test.");

        // An exception should be thrown because the folder does not exist
        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () ->
                                new BlockStreamConfig(
                                        generationMode,
                                        folderRootPath,
                                        delayBetweenBlockItems,
                                        blockStreamManagerImplementation,
                                        maxBlockItemsToStream,
                                        paddedLength,
                                        fileExtension,
                                        streamingMode,
                                        millisPerBlock));

        // Verify the exception message
        assertEquals("Folder does not exist: " + path, exception.getMessage());
    }

    @Test
    void testGenerationModeNonDirDoesNotCheckFolderExistence() {
        // Setup a non-existent folder path but with a generation mode that is not DIR
        String folderRootPath = "/non/existent/path/to/blocks";
        GenerationMode generationMode = GenerationMode.ADHOC;

        // No exception should be thrown because generation mode is not DIR
        BlockStreamConfig config =
                new BlockStreamConfig(
                        generationMode,
                        folderRootPath,
                        delayBetweenBlockItems,
                        blockStreamManagerImplementation,
                        maxBlockItemsToStream,
                        paddedLength,
                        fileExtension,
                        streamingMode,
                        millisPerBlock);

        // Verify that the configuration was created successfully
        assertEquals(folderRootPath, config.folderRootPath());
        assertEquals(GenerationMode.ADHOC, config.generationMode());
    }
}
