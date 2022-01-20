# Zilla Checkout Android SDK

Zilla connect is an easy, fast and secure way for your users to buy now and pay later from your app. It is a drop in framework that allows you host the Zilla checkout application within your android application and allow customers make payments using any of the available payment plans.

## Getting Started

<!-- Register on your Zilla Merchant  -->

1. Register on your [Zilla](https://merchant.usezilla.com/register) Merchant dashboard to get your public and secret keys.

## Installation

### Gradle

```sh
build.gradle
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
```

```sh
dependencies {
  implementation 'com.github.Zilla-tech:android-checkout-sdk:1.0.2-alpha
}
```

## Requirements
- Java 8 or higher
- The latest version of the Zilla Checkout Android SDK

Then in your code, create a new instance of the Zilla connect
Import Checkout SDK

```kotlin
import com.zilla.*
```

## Methods

There are two ways to make use of the Zilla Checkout Sdk

- [`completeExistingOrder()`](#completeExistingOrder)
  You can use this if have your own server and choose to create your order from your server [(see how)](https://www.notion.so/usezilla/Accepting-payments-5528b21e758244878d9b72acbdb8500c) to generate an `id`(`orderCode`) that you can pass as a parameter to your zilla connect instance.

- [`createNewOrder()`](#createNewOrder)
  You can use this if you want to create your order on the fly from your android application. Your order parameters are passed to the zilla checkout instance.

## Usage

### <a name="completeExistingOrder"></a> `completeExistingOrder`

These are the `required` parameters for `completeExistingOrder()`

- [`publicKey`](#key)
- [`callback`](https://github.com/Zilla-tech/android-checkout-sdk/blob/42065567713ca65989056d7500ca76c22309b2b2/android-checkout-sdk/src/main/java/com/zilla/ZillaTransactionCallback.kt)
- [`orderId`](#orderId)

e.g

```kotlin
val callback = object : ZillaTransactionCallback{
            override fun onClose() {}

            override fun onSuccess(paymentInfo: PaymentInfo) {}

            override fun onError(errorType: ErrorType) {}
        }

Zilla.instance.completeExistingOrder(
            this,
            "public_key",
            "orderId",
            callback)
```

### <a name="createNewOrder"></a> `openNew`

These are the `required` parameters for `openNew()`

- [`publicKey`](#key)
- [`callback`](https://github.com/Zilla-tech/android-checkout-sdk/blob/42065567713ca65989056d7500ca76c22309b2b2/android-checkout-sdk/src/main/java/com/zilla/ZillaTransactionCallback.kt)
- [`clientOrderReference`](#clientOrderReference)
- [`title`](#title)
- [`amount`](#amount)

e.g


```kotlin

  val params =
      ZillaParams.Builder(1000)
          .title("order title")
          .clientOrderReference("random unique ref")
          .redirectUrl("redirect_url")
          .productCategory("Fashion")
          .build()

  Zilla.instance.createNewOrder(
      this,
      "public_key",
      params,
      callback)
```

Read more about the transaction parameters in this [doc](https://github.com/Zilla-tech/web-checkout-sdk/tree/f00a8fae126763473a61e719ed473e50a85437e7#parameters)

## Support

If you're having general difficulties with Zilla Connect or your Sdk integration, please reach out to us at <boost@zilla.africa> or come chat with us on Slack. We're more than happy to help you out with your integration to Zilla.

## License

`MIT`
