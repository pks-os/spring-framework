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

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.mockito.Answers;
import org.mockito.MockSettings;

import org.springframework.test.context.bean.override.BeanOverride;

/**
 * {@code @MockitoBean} is an annotation that can be applied to a field in a test
 * class to override a bean in the test's
 * {@link org.springframework.context.ApplicationContext ApplicationContext}
 * using a Mockito mock.
 *
 * <p>If no explicit {@link #name() name} is specified, a target bean definition
 * is selected according to the type of the annotated field, and there must be
 * exactly one such candidate definition in the context. Otherwise, a {@code @Qualifier}
 * annotation can be used to help disambiguate between multiple candidates. If a
 * {@link #name() name} is specified, by default a corresponding bean definition
 * must exist in the application context. If you would like for a new bean definition
 * to be created when a corresponding bean definition does not exist, set the
 * {@link #enforceOverride() enforceOverride} attribute to {@code false}.
 *
 * <p>Dependencies that are known to the application context but are not beans
 * (such as those
 * {@linkplain org.springframework.beans.factory.config.ConfigurableListableBeanFactory#registerResolvableDependency(Class, Object)
 * registered directly}) will not be found, and a mocked bean will be added to
 * the context alongside the existing dependency.
 *
 * <p><strong>NOTE</strong>: Only <em>singleton</em> beans can be overridden.
 * Any attempt to override a non-singleton bean will result in an exception.
 *
 * @author Simon Baslé
 * @author Sam Brannen
 * @since 6.2
 * @see org.springframework.test.context.bean.override.mockito.MockitoSpyBean @MockitoSpyBean
 * @see org.springframework.test.context.bean.override.convention.TestBean @TestBean
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@BeanOverride(MockitoBeanOverrideProcessor.class)
public @interface MockitoBean {

	/**
	 * The name of the bean to register or replace.
	 * <p>If left unspecified, the bean to override is selected according to
	 * the annotated field's type.
	 * @return the name of the mocked bean
	 */
	String name() default "";

	/**
	 * Extra interfaces that should also be declared by the mock.
	 * <p>Defaults to none.
	 * @return any extra interfaces
	 * @see MockSettings#extraInterfaces(Class...)
	 */
	Class<?>[] extraInterfaces() default {};

	/**
	 * The {@link Answers} type to use in the mock.
	 * <p>Defaults to {@link Answers#RETURNS_DEFAULTS}.
	 * @return the answer type
	 */
	Answers answers() default Answers.RETURNS_DEFAULTS;

	/**
	 * Whether the generated mock is serializable.
	 * <p>Defaults to {@code false}.
	 * @return {@code true} if the mock is serializable
	 * @see MockSettings#serializable()
	 */
	boolean serializable() default false;

	/**
	 * The reset mode to apply to the mock.
	 * <p>The default is {@link MockReset#AFTER} meaning that mocks are
	 * automatically reset after each test method is invoked.
	 * @return the reset mode
	 */
	MockReset reset() default MockReset.AFTER;

	/**
	 * Whether to require the existence of a bean definition for the bean being
	 * overridden.
	 * <p>Defaults to {@code true} which means that an exception will be thrown
	 * if a corresponding bean definition does not exist.
	 * <p>Set to {@code false} to create a new bean definition when a corresponding
	 * bean definition does not exist.
	 * @see org.springframework.test.context.bean.override.BeanOverrideStrategy#REPLACE_DEFINITION
	 * @see org.springframework.test.context.bean.override.BeanOverrideStrategy#REPLACE_OR_CREATE_DEFINITION
	 */
	boolean enforceOverride() default true;

}
