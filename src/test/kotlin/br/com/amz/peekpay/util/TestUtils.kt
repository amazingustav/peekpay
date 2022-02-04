import java.math.BigDecimal

infix fun BigDecimal.equalsTo(other: BigDecimal) = this.compareTo(other) == 0
