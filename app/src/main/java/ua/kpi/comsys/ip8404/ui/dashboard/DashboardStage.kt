package ua.kpi.comsys.ip8404.ui.dashboard

sealed class DashboardStage {
    object Plot : DashboardStage()
    object Diagram : DashboardStage()
}
