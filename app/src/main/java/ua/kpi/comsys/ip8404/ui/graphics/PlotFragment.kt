package ua.kpi.comsys.ip8404.ui.graphics

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ua.kpi.comsys.ip8404.databinding.FragmentPlotBinding

class PlotFragment : Fragment() {
    private var _binding: FragmentPlotBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlotBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        binding.diagram.chunks = listOf(
//            Chunk(Color.GREEN, 35f),
//            Chunk(Color.YELLOW, 40f),
//            Chunk(Color.RED, 25f)
//        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}