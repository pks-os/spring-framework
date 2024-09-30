/*
 * Copyright 2002-2024 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.test.context.bean.override.mockito;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.quality.Strictness;

import org.springframework.context.annotation.Configuration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.when;
import static org.mockito.Mockito.mock;

/**
 * Integration tests for explicitly-defined {@link MockitoBeanSettings} with
 * lenient stubbing.
 *
 * @author Simon Baslé
 * @since 6.2
 */
@SpringJUnitConfig
@DirtiesContext
@MockitoBeanSettings(Strictness.LENIENT)
class MockitoBeanSettingsLenientIntegrationTests {

	@Test
	@SuppressWarnings("rawtypes")
	void unusedStubbingNotReported() {
		List list = mock();
		when(list.get(anyInt())).thenReturn(new Object());
	}

	@Configuration(proxyBeanMethods = false)
	static class Config {
		// no beans
	}

}
