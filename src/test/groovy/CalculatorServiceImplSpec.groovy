import com.airwallex.CalculatorService
import com.airwallex.CalculatorServiceImpl
import com.airwallex.Controller
import com.airwallex.Utils
import com.sun.media.sound.InvalidDataException
import org.junit.After
import org.junit.Before

class CalculatorServiceImplSpec extends CommonSetupSpec{

    def calculatorService = new CalculatorServiceImpl()

    @Before
    def cleanupCalculatorServiceSpec(){
        CalculatorService.stack.clear();
        CalculatorService.stackHistory.clear();
    }

    def "CalculatorService serviceOperands test"() {
        given:
        def input = new BigDecimal(2)

        when:
        calculatorService.serviceOperands(input)

        then:
        CalculatorService.stack.size() == 1
        CalculatorService.stack.peek() == input
    }

    def "CalculatorService serviceOperation test"() {
        given:
        def input = new BigDecimal(2)
        CalculatorService.stack.push(input)
        CalculatorService.stack.push(input)

        when:
        calculatorService.serviceOperation("+", true, 4)

        then:
        CalculatorService.stackHistory.size() == 2
        CalculatorService.stackHistory.peek() == [2,2]
        CalculatorService.stack.peek() == 4

    }

    def "CalculatorService serviceOperation test input starts with operation"() {
        given:
        def input = new BigDecimal(2)
        CalculatorService.stack.push(input)
        CalculatorService.stack.push(input)

        when:
        calculatorService.serviceOperation("-", false, 0)

        then:
        CalculatorService.stackHistory.size() == 1
        CalculatorService.stackHistory.peek() == [2,2]
        CalculatorService.stack.peek() == 0
    }


    def "CalculatorService serviceOperation test multiply operation"() {
        given:
        def input = new BigDecimal(4)
        CalculatorService.stack.push(input)
        CalculatorService.stack.push(input)

        when:
        calculatorService.serviceOperation("*", false, 0)

        then:
        CalculatorService.stackHistory.size() == 1
        CalculatorService.stackHistory.peek() == [4,4]
        CalculatorService.stack.peek() == 16
    }

    def "CalculatorService serviceOperation test divide operation"() {
        given:
        def input = new BigDecimal(3)
        CalculatorService.stack.push(input)
        CalculatorService.stack.push(input)

        when:
        calculatorService.serviceOperation("/", false, 0)

        then:
        CalculatorService.stackHistory.size() == 1
        CalculatorService.stackHistory.peek() == [3,3]
        CalculatorService.stack.peek() == 1
    }

    def "CalculatorService serviceOperation test sqrt operation"() {
        given:
        def input = new BigDecimal(4)
        CalculatorService.stack.push(input)

        when:
        calculatorService.serviceOperation("sqrt", false, 0)

        then:
        CalculatorService.stackHistory.size() == 0
        CalculatorService.stack.peek() == 2
    }

    def "CalculatorService serviceOperation test sqrt operation in memopry stack precision to be 15 digits"() {
        given:
        def input = new BigDecimal(3)
        CalculatorService.stack.push(input)

        when:
        calculatorService.serviceOperation("sqrt", false, 0)

        then:
        CalculatorService.stackHistory.size() == 0
        def result = CalculatorService.stack.peek()
        result == 1.732050807568877
        result.toPlainString().length() == 17
    }

    def "CalculatorService serviceOperation test undo operation"() {
        given:
        def input = new BigDecimal(4)
        CalculatorService.stack.push(input)

        when:
        calculatorService.serviceOperation("undo", false, 0)

        then:
        CalculatorService.stackHistory.size() == 0
        CalculatorService.stack.size() == 0
    }

    def "CalculatorService serviceOperation test clear operation"() {
        given:
        def input = new BigDecimal(4)
        CalculatorService.stack.push(input)

        when:
        calculatorService.serviceOperation("clear", false, 0)

        then:
        CalculatorService.stackHistory.size() == 1
        CalculatorService.stackHistory.peek() == [4]
        CalculatorService.stack.size() == 0
    }

    def "CalculatorService serviceOperation test addition operation in memory stack precision to be 15 digits"() {
        given:
        def input = new BigDecimal(1.732050807568877)
        CalculatorService.stack.push(input)
        CalculatorService.stack.push(input)

        when:
        calculatorService.serviceOperation("+", false, 0)

        then:
        CalculatorService.stackHistory.size() == 1
        def result = CalculatorService.stack.peek()
        result == 3.464101615137753
        result.toPlainString().length() == 17
    }
}
