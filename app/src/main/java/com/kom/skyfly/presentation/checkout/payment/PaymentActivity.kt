package com.kom.skyfly.presentation.checkout.payment

import IssueTicketBottomSheets
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
import com.kom.skyfly.R
import com.kom.skyfly.databinding.ActivityPaymentBinding

class PaymentActivity : AppCompatActivity() {
    var btnCloseSnap: AppCompatButton? = null
    private val binding: ActivityPaymentBinding by lazy {
        ActivityPaymentBinding.inflate(layoutInflater)
    }
    private var transactionId: String? = null
    private var adult: Int = 0
    private var children: Int = 0
    private var baby: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHeaderTitle()
        btnCloseSnap = binding.btnNext
        btnCloseSnap!!.setOnClickListener(
            View.OnClickListener { view: View? ->
                transactionId?.let { openIssueTicketBottomSheets(it, adult, children, baby) }
            },
        )
        setContentView(binding.root)
        val paymentUrl = intent.getStringExtra("payment_url")
        transactionId = intent.getStringExtra("EXTRAS_TRANSACTION_ID")
        adult = intent.getIntExtra("EXTRAS_ADULT", 0)
        children = intent.getIntExtra("EXTRAS_CHILD", 0)
        baby = intent.getIntExtra("EXTRAS_BABY", 0)
        if (!paymentUrl.isNullOrEmpty()) {
            openUrlFromWebView(paymentUrl)
        }
    }

    private fun setHeaderTitle() {
        binding.layoutHeader.tvTitleHeader.setText(R.string.text_payment)
        binding.layoutHeader.ivBack.setOnClickListener {
            onBackPressed()
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

    private fun openIssueTicketBottomSheets(
        transactionId: String,
        adult: Int,
        child: Int,
        baby: Int,
    ) {
        if (!supportFragmentManager.isStateSaved) {
            val bottomSheetFragment =
                IssueTicketBottomSheets.newInstance(transactionId, adult, child, baby)
            bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
        }
    }
}
