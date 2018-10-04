
import com.airwallex.Utils
import org.junit.Before


class CommonSetupSpec extends spock.lang.Specification{

    @Before
    def "setupControllerSpec"(){
        Utils.populateProperties()
    }
}
