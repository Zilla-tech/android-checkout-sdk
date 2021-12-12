package com.zilla.view

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.zilla.EventListener
import com.zilla.Zilla
import com.zilla.ZillaWebInterface
import com.zilla.checkout_android.R
import com.zilla.checkout_android.databinding.ActivityZillaBinding
import com.zilla.commons.*
import com.zilla.di.AppContainer
import com.zilla.di.ViewModelConstructor
import com.zilla.di.ViewModelFactory
import com.zilla.model.Environment
import com.zilla.model.ErrorType
import com.zilla.model.PaymentInfo
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class ZillaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityZillaBinding

    private lateinit var viewModel: ZillaViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        binding = ActivityZillaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        prepareWebView()
        viewModel.initiateTransaction()
    }

    private fun setupView() {
        val appContainer = AppContainer(Environment.DEV)
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory.build(object : ViewModelConstructor {
                override fun create(): ViewModel {
                    return ZillaViewModel(appContainer.zillaRepository)
                }
            })
        )[ZillaViewModel::class.java]

        viewModel.apply {

            lifecycleScope.launch {

                loadingStatus.collect {
                    if (it) {
                        showLoading()
                    } else {
                        dismissLoading()
                    }
                }
            }

            lifecycleScope.launch {

                errorStatus.collect {
                    onError(it)
                }
            }

            lifecycleScope.launch {
                orderValidationSuccessful.collect {
                    Logger.log(this, "isOrderIdValid fired")

                    hideAllViewsAndShowWebView()
                    loadUrl(it.paymentLink)
                }
            }

            lifecycleScope.launch {
                createWithPublicKeyInfo.collect {
                    Logger.log(this, "createWithPublicKeyInfo fired")
                    hideAllViewsAndShowWebView()
                    loadUrl(it.paymentLink)
                }
            }
        }
    }

    private fun hideAllViewsAndShowWebView() {
        binding.webView.visibility = View.VISIBLE
        binding.webViewContainer.visibility = View.VISIBLE
    }

    @SuppressLint("NewApi", "SetJavaScriptEnabled")
    private fun prepareWebView() {
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.settings.domStorageEnabled = true
        binding.webView.isScrollbarFadingEnabled = false
        binding.webView.isVerticalScrollBarEnabled = true
        binding.webView.isHorizontalScrollBarEnabled = true
        binding.webView.scrollBarStyle = WebView.SCROLLBARS_OUTSIDE_OVERLAY
        binding.webView.settings.useWideViewPort = true
        binding.webView.settings.loadWithOverviewMode = true

        try {
            binding.webView.settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        } catch (e: Exception) {
        }

        binding.webView.webViewClient = webViewClient
        binding.webView.webChromeClient = webViewChromeClient

        val webInterface = ZillaWebInterface( eventListener)

        binding.webView.addJavascriptInterface(webInterface, "ZillaWebInterface")
    }

    private fun closeWebView() {

    }

    private fun loadUrl(paymentLink: String?) {
        Logger.log(this, "Load URL called $paymentLink")
        paymentLink?.let {
            binding.webView.loadUrl(it)
        }
    }

    // Loading Indicator implementation
    private fun isLoading(): Boolean {
        return findViewById<RelativeLayout>(R.id.progress_mask).isVisible
    }

    private fun showLoading() {
        if (isLoading()) return
        hideKeyBoard()
        findViewById<View>(R.id.progress_mask).show()
        findViewById<CircularProgressIndicator>(R.id.progress_indicator).show()
        disableTouch()
    }

    private fun dismissLoading() {
        findViewById<RelativeLayout>(R.id.progress_mask).visibility = View.GONE
        findViewById<CircularProgressIndicator>(R.id.progress_indicator).hide()
        enableTouch()
    }

    private fun onError(errorType: ErrorType) {
        Zilla.instance.callback.onError(errorType)
        finish()
    }

    private val webViewChromeClient = object : WebChromeClient() {
        override fun onProgressChanged(view: WebView, progress: Int) {
            binding.webViewProgressBar.progress = progress
        }
    }

    private val webViewClient = object : WebViewClient() {

        override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
            Logger.log(this, "Current $url loading in WebView")
            super.onPageStarted(view, url, favicon)
            binding.webViewProgressBar.visibility = View.VISIBLE
        }

        override fun onPageFinished(view: WebView, url: String) {
            super.onPageFinished(view, url)
            binding.webViewProgressBar.visibility = View.GONE

            Logger.log(this, "onPageFinished $url loading in WebView")
        }
    }

    private val eventListener: EventListener = object : EventListener {
        override fun onClose() {
            Toast.makeText(this@ZillaActivity, "Close event", Toast.LENGTH_LONG).show()
            Zilla.instance.callback.onClose()
            finish()
        }

        override fun onSuccess(paymentInfo: PaymentInfo) {
            Toast.makeText(this@ZillaActivity, "Close event", Toast.LENGTH_LONG).show()
            Zilla.instance.callback.onSuccess(paymentInfo)
            finish()
        }
    }
}