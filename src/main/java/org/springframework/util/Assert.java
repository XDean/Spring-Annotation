/*
 * Copyright 2002-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package org.springframework.util;

import java.util.function.Supplier;

import org.springframework.lang.Nullable;

/**
 * Assertion utility class that assists in validating arguments.
 *
 * <p>
 * Useful for identifying programmer errors early and clearly at runtime.
 *
 * <p>
 * For example, if the contract of a public method states it does not allow {@code null} arguments,
 * {@code Assert} can be used to validate that contract. Doing this clearly indicates a contract
 * violation when it occurs and protects the class's invariants.
 *
 * <p>
 * Typically used to validate method arguments rather than configuration properties, to check for
 * cases that are usually programmer errors rather than configuration errors. In contrast to
 * configuration initialization code, there is usually no point in falling back to defaults in such
 * methods.
 *
 * <p>
 * This class is similar to JUnit's assertion library. If an argument value is deemed invalid, an
 * {@link IllegalArgumentException} is thrown (typically). For example:
 *
 * <pre class="code">
 * Assert.notNull(clazz, "The class must not be null");
 * Assert.isTrue(i > 0, "The value must be greater than zero");
 * </pre>
 *
 * <p>
 * Mainly for internal use within the framework; consider
 * <a href="http://commons.apache.org/proper/commons-lang/">Apache's Commons Lang</a> for a more
 * comprehensive suite of {@code String} utilities.
 *
 * @author Keith Donald
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @author Colin Sampaleanu
 * @author Rob Harrop
 * @since 1.1.2
 */
public abstract class Assert {

  /**
   * Assert a boolean expression, throwing an {@code IllegalStateException} if the expression
   * evaluates to {@code false}.
   * <p>
   * Call {@link #isTrue} if you wish to throw an {@code IllegalArgumentException} on an assertion
   * failure.
   *
   * <pre class="code">
   * Assert.state(id == null, "The id property must not already be initialized");
   * </pre>
   *
   * @param expression a boolean expression
   * @param message the exception message to use if the assertion fails
   * @throws IllegalStateException if {@code expression} is {@code false}
   */
  public static void state(boolean expression, String message) {
    if (!expression) {
      throw new IllegalStateException(message);
    }
  }

  /**
   * Assert a boolean expression, throwing an {@code IllegalArgumentException} if the expression
   * evaluates to {@code false}.
   *
   * <pre class="code">
   * Assert.isTrue(i &gt; 0, "The value must be greater than zero");
   * </pre>
   *
   * @param expression a boolean expression
   * @param message the exception message to use if the assertion fails
   * @throws IllegalArgumentException if {@code expression} is {@code false}
   */
  public static void isTrue(boolean expression, String message) {
    if (!expression) {
      throw new IllegalArgumentException(message);
    }
  }

  /**
   * Assert a boolean expression, throwing an {@code IllegalArgumentException} if the expression
   * evaluates to {@code false}.
   *
   * <pre class="code">
   * Assert.isTrue(i &gt; 0, () -&gt; "The value '" + i + "' must be greater than zero");
   * </pre>
   *
   * @param expression a boolean expression
   * @param messageSupplier a supplier for the exception message to use if the assertion fails
   * @throws IllegalArgumentException if {@code expression} is {@code false}
   * @since 5.0
   */
  public static void isTrue(boolean expression, Supplier<String> messageSupplier) {
    if (!expression) {
      throw new IllegalArgumentException(nullSafeGet(messageSupplier));
    }
  }

  /**
   * Assert that an object is not {@code null}.
   *
   * <pre class="code">
   * Assert.notNull(clazz, "The class must not be null");
   * </pre>
   *
   * @param object the object to check
   * @param message the exception message to use if the assertion fails
   * @throws IllegalArgumentException if the object is {@code null}
   */
  public static void notNull(@Nullable Object object, String message) {
    if (object == null) {
      throw new IllegalArgumentException(message);
    }
  }

  /**
   * Assert that an object is not {@code null}.
   *
   * <pre class="code">
   * Assert.notNull(clazz, () -&gt; "The class '" + clazz.getName() + "' must not be null");
   * </pre>
   *
   * @param object the object to check
   * @param messageSupplier a supplier for the exception message to use if the assertion fails
   * @throws IllegalArgumentException if the object is {@code null}
   * @since 5.0
   */
  public static void notNull(@Nullable Object object, Supplier<String> messageSupplier) {
    if (object == null) {
      throw new IllegalArgumentException(nullSafeGet(messageSupplier));
    }
  }

  /**
   * Assert that the given String contains valid text content; that is, it must not be {@code null}
   * and must contain at least one non-whitespace character.
   *
   * <pre class="code">
   * Assert.hasText(name, "'name' must not be empty");
   * </pre>
   *
   * @param text the String to check
   * @param message the exception message to use if the assertion fails
   * @see StringUtils#hasText
   * @throws IllegalArgumentException if the text does not contain valid text content
   */
  public static void hasText(@Nullable String text, String message) {
    if (!StringUtils.hasText(text)) {
      throw new IllegalArgumentException(message);
    }
  }

  @Nullable
  private static String nullSafeGet(@Nullable Supplier<String> messageSupplier) {
    return (messageSupplier != null ? messageSupplier.get() : null);
  }
}
