import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kom.skyfly.R
import com.kom.skyfly.databinding.FragmentBottomSheetsIssueTicketBinding
import com.kom.skyfly.presentation.checkout.flightdetail.FlightDetailActivity

class IssueTicketBottomSheets : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentBottomSheetsIssueTicketBinding
    private var listener: SearchView.OnCloseListener? = null

    companion object {
        fun newInstance(
            transactionId: String,
            adult: Int,
            child: Int,
            baby: Int,
        ): IssueTicketBottomSheets {
            val fragment = IssueTicketBottomSheets()
            val args =
                Bundle().apply {
                    putString("transactionId", transactionId)
                    putInt("adult", adult)
                    putInt("child", child)
                    putInt("baby", baby)
                }
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentBottomSheetsIssueTicketBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        setClickListeners()
        val maxPeekHeight =
            resources.getDimensionPixelSize(
                R.dimen.max_bottom_sheet_height,
            )
        setBottomSheetMaxHeight(maxPeekHeight)
    }

    private fun setClickListeners() {
        binding.btnIssueTicket.setOnClickListener {
            navigateToFlightDetail()
            listener?.onClose()
            dismiss()
        }
        binding.ivClose.setOnClickListener {
            listener?.onClose()
            dismiss()
        }
    }

    private fun navigateToFlightDetail() {
        val transactionId = arguments?.getString("transactionId")
        val adult = arguments?.getInt("adult")
        val child = arguments?.getInt("child")
        val baby = arguments?.getInt("baby")
        val intent =
            Intent(requireContext(), FlightDetailActivity::class.java).apply {
                putExtra("EXTRAS_TRANSACTION_ID", transactionId)
                putExtra("EXTRAS_ADULT", adult)
                putExtra("EXTRAS_CHILD", child)
                putExtra("EXTRAS_BABY", baby)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
        startActivity(intent)
    }

    private fun setBottomSheetMaxHeight(maxHeight: Int) {
        dialog?.setOnShowListener {
            val bottomSheetDialog = it as? BottomSheetDialog
            val bottomSheet =
                bottomSheetDialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            bottomSheet?.let { sheet ->
                val behavior = BottomSheetBehavior.from(sheet)
                behavior.peekHeight = maxHeight
                behavior.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        }
    }
}
