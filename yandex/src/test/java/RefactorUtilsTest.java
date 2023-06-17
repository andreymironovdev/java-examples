import com.andreymironov.yandex.RefactorUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class RefactorUtilsTest {
    @Test
    public void should_order() {
        RefactorUtils.MethodInfo baz = new RefactorUtils.MethodInfo("baz", List.of());
        RefactorUtils.MethodInfo tr = new RefactorUtils.MethodInfo("tr", List.of());
        RefactorUtils.MethodInfo bar = new RefactorUtils.MethodInfo("bar", List.of(baz, tr));
        RefactorUtils.MethodInfo foo = new RefactorUtils.MethodInfo("foo", List.of(bar));
        RefactorUtils.MethodInfo main = new RefactorUtils.MethodInfo("main", List.of(foo));

        Assertions.assertThat(RefactorUtils.getMethodNamesToRefactor(List.of(
                main, foo, bar, baz, tr
        ))).isIn("baz tr bar foo main", "tr baz bar foo main");
    }
}