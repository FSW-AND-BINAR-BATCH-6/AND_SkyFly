package com.kom.skyfly.presentation.checkout.payment

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.kom.skyfly.databinding.ActivityPaymentBinding

class PaymentActivity : AppCompatActivity() {
    private val binding: ActivityPaymentBinding by lazy {
        ActivityPaymentBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val paymentUrl = intent.getStringExtra("payment_url")
        if (!paymentUrl.isNullOrEmpty()) {
            openUrlFromWebView(paymentUrl)
        }
        openUrlFromWebView(paymentUrl)
    }

    private fun openUrlFromWebView(url: String?) {
        val webView: WebView = binding.wvPayment
        webView.settings.loadsImagesAutomatically = true
        webView.settings.javaScriptEnabled = true
        if (url != null) {
            webView.loadUrl(url)
        }
        webView.webViewClient =
            object : WebViewClient() {
                val pd = ProgressDialog(this@PaymentActivity)

                override fun shouldOverrideUrlLoading(
                    view: WebView,
                    request: WebResourceRequest,
                ): Boolean {
                    val requestUrl = request.url.toString()
                    Log.d("WebView", "Request URL: $requestUrl")
                    if (requestUrl.contains("gojek://") ||
                        requestUrl.contains("shopeeid://") ||
                        requestUrl.contains("//wsa.wallet.airpay.co.id/") ||
                        requestUrl.contains("/gopay/partner/") ||
                        requestUrl.contains("/shopeepay/")
                    ) {
                        val intent = Intent(Intent.ACTION_VIEW, request.url)
                        startActivity(intent)
                        return true
                    }
                    return false
                }

                override fun onPageStarted(
                    view: WebView,
                    url: String,
                    favicon: Bitmap?,
                ) {
                    Log.d("WebView", "Page started: $url")
                    pd.setMessage("Loading...")
                    pd.show()
                    super.onPageStarted(view, url, favicon)
                }

                override fun onPageFinished(
                    view: WebView,
                    url: String,
                ) {
                    Log.d("WebView", "Page finished: $url")
                    pd.dismiss()
                    super.onPageFinished(view, url)
                }
            }
    }
}
