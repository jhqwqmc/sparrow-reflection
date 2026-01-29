package net.momirealms.sparrow.reflection.field.matcher;

public interface FieldMatchers {

    static FieldMatcher any() {
        return AnyMatcher.INSTANCE;
    }

    static FieldMatcher anyOf(final FieldMatcher... matchers) {
        return new AnyOfMatcher(matchers);
    }

    static FieldMatcher allOf(final FieldMatcher... matchers) {
        return new AllOfMatcher(matchers);
    }

    static FieldMatcher not(final FieldMatcher matcher) {
        return new NotMatcher(matcher);
    }

    static FieldMatcher noneOf(final FieldMatcher... matchers) {
        return not(anyOf(matchers));
    }

    static FieldMatcher named(String name) {
        return new NameMatcher(name, true);
    }

    static FieldMatcher namedNoRemap(String name) {
        return new NameMatcher(name, false);
    }

    static FieldMatcher named(String... names) {
        return new NamesMatcher(names, true);
    }

    static FieldMatcher namedNoRemap(String... names) {
        return new NamesMatcher(names, false);
    }

    static FieldMatcher type(Class<?> clazz) {
        return new TypeMatcher(clazz);
    }

    static FieldMatcher type(net.momirealms.sparrow.reflection.type.matcher.TypeMatcher typeMatcher) {
        return new GenericTypeMatcher(typeMatcher);
    }

    static FieldMatcher privateMethod() {
        return PrivateMatcher.INSTANCE;
    }

    static FieldMatcher publicMethod() {
        return PublicMatcher.INSTANCE;
    }

    static FieldMatcher protectedMethod() {
        return ProtectedMatcher.INSTANCE;
    }

    static FieldMatcher staticMethod() {
        return StaticMatcher.INSTANCE;
    }

    static FieldMatcher instanceMethod() {
        return InstanceMatcher.INSTANCE;
    }

    static FieldMatcher finalMethod() {
        return FinalMatcher.INSTANCE;
    }
}
