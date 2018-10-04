import com.airwallex.CalculatorService
import com.airwallex.CalculatorServiceImpl
import com.airwallex.Controller
import com.sun.media.sound.InvalidDataException
import org.junit.Assume
import org.junit.Before
import spock.lang.IgnoreIf

class End2EndTestSpec extends CommonSetupSpec {
    def calculatorService = new CalculatorServiceImpl()
    def controller = new Controller(calculatorService)

    @Before
    def cleanupCalculatorServiceSpec(){
        CalculatorService.stack.clear();
        CalculatorService.stackHistory.clear();
    }

    def "RPN Calculator Test for Example 1"() {
        given:
        def input = new Scanner("5 2")

        when:
        controller.serviceInputs(input)

        then:
        CalculatorService.stack == [5,2]
    }

    def "RPN Calculator Test for Example 2"() {
        given:
        def input = new Scanner("2 sqrt \n clear 9 sqrt")

        when:
        controller.serviceInputs(input)

        then:
        CalculatorService.stack == [3]
        CalculatorService.stackHistory.size() == 5
        CalculatorService.stackHistory.get(2) == [1.414213562373095] //in memory has 15 digits but 10 gets displayed
    }

    def "RPN Calculator Test for Example 3"() {
        given:
        def input = new Scanner("5 2 - \n 3 - \n clear")

        when:
        controller.serviceInputs(input)

        then:
        CalculatorService.stack == []
        CalculatorService.stackHistory.size() == 8
        CalculatorService.stackHistory.get(4) == [3]
        CalculatorService.stackHistory.peek() == [0]


    }

    def "RPN Calculator Test for Example 4"() {
        given:
        def input = new Scanner("5 4 3 2 \n undo undo * \n 5 * \n undo")

        when:
        controller.serviceInputs(input)

        then:
        CalculatorService.stack.get(0) == 20
        CalculatorService.stack.get(1) == 5
    }

    def "RPN Calculator Test for Example 5"() {
        given:
        def input = new Scanner("7 12 2 / \n * \n 4 /")

        when:
        controller.serviceInputs(input)

        then:
        CalculatorService.stack == [10.5]
        CalculatorService.stackHistory.size() == 9
        CalculatorService.stackHistory.get(5) == [7,6]
        CalculatorService.stackHistory.get(6) == [42]
    }

    def "RPN Calculator Test for Example 6"() {
        given:
        def input = new Scanner("1 2 3 4 5 \n * \n clear 3 4 -")

        when:
        controller.serviceInputs(input)

        then:
        CalculatorService.stack == [-1]
        CalculatorService.stackHistory.size() == 11
        CalculatorService.stackHistory.get(5) == [1,2,3,4,5]
        CalculatorService.stackHistory.get(6) == [1,2,3,2E+1]
        CalculatorService.stackHistory.get(6).peek().toPlainString() == "20"
    }

    def "RPN Calculator Test for Example 7"() {
        given:
        def input = new Scanner("1 2 3 4 5 \n * * * *")

        when:
        controller.serviceInputs(input)

        then:
        CalculatorService.stack == [120]
        CalculatorService.stackHistory.size() == 9
        CalculatorService.stackHistory.get(5) == [1,2,3,4,5]
    }

    def "RPN Calculator Test for Example 8"() {
        given:
        def input = new Scanner("1 2 3 * 5 + * * 6 5")

        when:
        controller.serviceInputs(input)

        then:
        CalculatorService.stackHistory.peek() == [11]

    }

}
