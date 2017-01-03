package example

import akka.event.LoggingAdapter
import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.google.inject.Inject
import com.typesafe.config.Config
import memory.Slot

/**
  * Created by markmo on 6/10/2016.
  */
class Form @Inject()(config: Config,
                     logger: LoggingAdapter) {

  val addressApi = config.getString("services.cooee.address.url")

  val data = Map(
    "purchase" -> Slot(
      "purchase",
      children = Some(List(
        Slot(
          "time",
          Some("When are you next available?"),
          prompt = Some("time")
        ),
        Slot(
          "name",
          Some("Please provide your full name as <first name> <last name>"),
          children = Some(List(
            Slot(
              "firstName",
              Some("What is your first name?")
            ),
            Slot(
              "lastName",
              Some("What is your last name?")
            ))
          ),
          parseExpr = Some(
            """
              |function (value) {
              |  var re = /(\S+)\s+(.*)/;
              |  var match = re.exec(value);
              |  return {
              |    firstName: match[1],
              |    lastName: match[2]
              |  };
              |}
            """.stripMargin)
        ),
        Slot(
          "planChoice",
          Some("Please choose your plan"),
          enum = Some(List(
            "12 mo - BYO mobile - 500 MB",
            "12 mo - BYO mobile - 5 GB",
            "12 mo - BYO mobile - 10 GB",
            "2 yr - 1 GB",
            "2 yr - 3 GB",
            "2 yr - 10 GB",
            "2 yr - 20 GB",
            "2 yr - 30 GB",
            "Casual SIM - 500 MB",
            "Casual SIM - 5 GB",
            "Casual SIM - 10 GB"
          )),
          confirm = Some("Is this correct?")
        ),
        Slot(
          "phone",
          Some("What is your phone number?"),
          validateFn = Some(value => {
            val phoneUtil = PhoneNumberUtil.getInstance()
            try {
              val auNumberProto = phoneUtil.parse(value, "AU")
              phoneUtil.isValidNumber(auNumberProto)
            } catch {
              case e: NumberFormatException => false
            }
          }),
          invalidMessage = Some("Invalid format. Please try again.")
        ),
        Slot(
          "paymentMethod",
          children = Some(List(
            Slot(
              "cardholderName",
              Some("What is the card holder's name?")
            ),
            Slot(
              "cardNumber",
              Some("What is the card number?"),
              caption = Some("Card number ending in")
            ),
            Slot(
              "securityCode",
              Some("What is the security code for the card?")
            ),
            Slot(
              "expiryDate",
              Some("Please provide the expiry date as e.g. 01/18"),
              children = Some(List(
                Slot("expiryMonth"),
                Slot("expiryYear")
              )),
              parseExpr = Some(
                """
                  |function (value) {
                  |  var re = /(\d+{1,2})/(\d+{2})/;
                  |  var match = re.exec(value);
                  |  return {
                  |    expiryMonth: match[1],
                  |    expiryYear: match[2]
                  |  };
                  |}
                """.stripMargin)
            )
          )),
          confirm = Some("Are the following payment details correct?")
        ),
        Slot(
          "address",
          Some("Please provide your address as <street> <city> <state> <postcode>"),
          children = Some(List(
            Slot(
              "street1",
              Some("What is your street as <street number> <street name> <street type>?")
            ),
            Slot(
              "city",
              Some("What is your city?")
            ),
            Slot(
              "state",
              Some("What is your state?")
            ),
            Slot(
              "postcode",
              Some("What is your postcode?")
            ),
            Slot(
              "country",
              Some("What is your country?")
            ),
            Slot(
              "latitude",
              Some("What is the latitude of your location?")
            ),
            Slot(
              "longitude",
              Some("What is the longitude of your location?")
            )
          )),
          parseApi = Some(addressApi),
          parseExpr = Some(
            """
              |function (value) {
              |  return {
              |    street1: value.street1,
              |    city: value.city,
              |    state: value.state,
              |    postcode: value.postcode,
              |    country: value.country,
              |    latitude: value.latitude,
              |    longitude: value.longitude
              |  };
              |}
            """.stripMargin),
          confirm = Some("Is this address correct?")
        ),
        Slot(
          "coupon",
          Some("Please provide any coupon code, or reply with 'none'")
        )
      ))
    )
  )

}
