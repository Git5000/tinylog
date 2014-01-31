/*
 * Copyright 2014 Martin Winandy
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

package org.pmw.benchmark.log4j2;

import java.io.File;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.appender.FileAppender;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.pmw.benchmark.IBenchmark;

public class Log4j2Benchmark implements IBenchmark {

	private Logger logger;
	private Appender appender;

	@Override
	public String getName() {
		return "log4j 2";
	}

	@Override
	public void init(final File file) throws Exception {
		logger = createLogger();
		Configuration configuration = logger.getContext().getConfiguration();

		for (Appender appender : configuration.getAppenders().values()) {
			logger.removeAppender(appender);
		}
		appender = createAppender(file, configuration);
		logger.addAppender(appender);

		logger.setLevel(Level.INFO);
	}

	@Override
	public void trace(final int index) {
		logger.trace("Trace: {}, PI: {}", index, Math.PI);
	}

	@Override
	public void debug(final int index) {
		logger.debug("Debug: {}, PI: {}", index, Math.PI);
	}

	@Override
	public void info(final int index) {
		logger.info("Info: {}, PI: {}", index, Math.PI);
	}

	@Override
	public void warning(final int index) {
		logger.warn("Warning: {}, PI: {}", index, Math.PI);
	}

	@Override
	public void error(final int index) {
		logger.error("Error: {}, PI: {}", index, Math.PI);
	}

	@Override
	public void dispose() throws Exception {
		appender.stop();
	}

	protected Logger createLogger() {
		return (Logger) LogManager.getRootLogger();
	}

	protected Appender createAppender(final File file, final Configuration configuration) {
		PatternLayout layout = PatternLayout.createLayout("%d{yyyy-MM-dd HH:mm:ss} [%t] %C.%M(): %m%n", configuration, null, null, null);
		return FileAppender.createAppender(file.getAbsolutePath(), null, null, "File", null, null, null, layout, null, null, null, configuration);
	}

}
