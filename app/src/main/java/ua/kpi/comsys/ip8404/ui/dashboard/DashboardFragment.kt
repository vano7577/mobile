package ua.kpi.comsys.ip8404.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import ua.kpi.comsys.ip8404.R
import ua.kpi.comsys.ip8404.databinding.FragmentDashboardBinding
import ua.kpi.comsys.ip8404.ui.graphics.DiagramFragment
import ua.kpi.comsys.ip8404.ui.graphics.PlotFragment

class DashboardFragment : Fragment() {
    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: DashboardViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        viewModel =
                ViewModelProvider(this).get(DashboardViewModel::class.java)
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.stage.observe(viewLifecycleOwner, Observer {
            when(it) {
                DashboardStage.Diagram -> diagram()
                DashboardStage.Plot -> plot()
            }
        })

        binding.diagram.setOnClickListener {
            viewModel.changeStage(DashboardStage.Diagram)
        }

        binding.plot.setOnClickListener {
            viewModel.changeStage(DashboardStage.Plot)
        }
    }

    private fun diagram() {
        changeFragment(DiagramFragment())
    }

    private fun plot() {
        changeFragment(PlotFragment())
    }

    private fun changeFragment(fragment: Fragment) {
        childFragmentManager.commit {
            replace(R.id.graphHolder, fragment)
        }
    }
}