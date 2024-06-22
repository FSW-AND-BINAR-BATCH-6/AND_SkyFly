package com.kom.skyfly.presentation.checkout.payment

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.RenderProcessGoneDetail
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.kom.skyfly.databinding.ActivityPaymentBinding
import com.kom.skyfly.presentation.login.LoginActivity
import com.kom.skyfly.presentation.main.MainActivity
import com.kom.skyfly.presentation.register.RegisterActivity

class PaymentActivity : AppCompatActivity() {
    var btnCloseSnap: AppCompatButton? = null
    private val binding: ActivityPaymentBinding by lazy {
        ActivityPaymentBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        btnCloseSnap = binding.btnClose
        btnCloseSnap!!.setOnClickListener(
            View.OnClickListener { view: View? ->
                val intent =
                    Intent(
                        this,
                        MainActivity::class.java,
                    )
                startActivity(intent)
            },
        )
        setContentView(binding.root)
        val paymentUrl = intent.getStringExtra("payment_url")
        if (!paymentUrl.isNullOrEmpty()) {
            openUrlFromWebView(paymentUrl)
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun openUrlFromWebView(url: String?) {
        val webView: WebView = binding.wvPayment
        webView.settings.loadsImagesAutomatically = true
        webView.settings.javaScriptEnabled = true
        if (url != null) {
            webView.loadUrl(url)
        } else {
            finish()
            webView.destroy()
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
                    } else {
                        return false
                    }
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

                override fun onRenderProcessGone(
                    view: WebView?,
                    detail: RenderProcessGoneDetail?,
                ): Boolean {
                    return super.onRenderProcessGone(view, detail)
                }
            }
        webView.getSettings().setLoadsImagesAutomatically(true)
        webView.getSettings().setJavaScriptEnabled(true)
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY)
    }

    private fun handlePaymentSuccess() {
        // Handle payment success, e.g., show a success message or navigate to another activity
        Log.d("PaymentActivity", "Payment was successful")
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun handlePaymentCancel() {
        // Handle payment cancellation, e.g., show a cancellation message or navigate to another activity
        Log.d("PaymentActivity", "Payment was canceled")
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
        finish()
    }
}
