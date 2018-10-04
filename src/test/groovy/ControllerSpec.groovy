import com.airwallex.CalculatorServiceImpl
import com.airwallex.Controller
import com.sun.media.sound.InvalidDataException


class ControllerSpec extends CommonSetupSpec{

    def calculatorService = Mock(CalculatorServiceImpl)
    def controller = new Controller(calculatorService)


    def "Controller serviceInputs test single line input"() {
        given:
        def input = new Scanner("2 3 +")

        when:
        controller.serviceInputs(input)

        then:
        2 * calculatorService.serviceOperands(_ as BigDecimal)
        1 * calculatorService.serviceOperation("+", true, 4)
    }

    def "Controller serviceInputs test multi line input"() {
        given:
        def input = new Scanner("2 4 - \n 2 4")


        when:
        controller.serviceInputs(input)

        then:
        4 * calculatorService.serviceOperands(_ as BigDecimal)
        1 * calculatorService.serviceOperation("-", true, 4)
    }


    def "Controller serviceInputs test ExceptionHandling"() {
        given:

        def input = new Scanner("2 invalid -")


        when:
        controller.serviceInputs(input)

        then:
        1 * calculatorService.serviceOperands(_ as BigDecimal)
        1 * calculatorService.serviceOperation('invalid', true, 2) >> { throw new InvalidDataException("Invalid") }

        then:
        thrown InvalidDataException
    }
}
