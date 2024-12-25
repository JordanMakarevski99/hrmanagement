import { OnInit, Component } from '@angular/core';
import { ChartData, ChartOptions } from 'chart.js';
import { EmployeeService } from 'src/app/services/employee.service';
import { LeaveRequestService } from 'src/app/services/leave-request.service';
import { DepartmentService } from 'src/app/services/department.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {

  constructor(
    private employeeService: EmployeeService,
    private leaveRequestService: LeaveRequestService,
    private departmentService: DepartmentService
  ) { }

  numberOfEmployees: number = 0;
  numberOfDepartments: number = 0;
  leaveRequestsByStatus: any = {};
  employeeToDepartmentRatio: number = 0;

  employeeToDepartmentRatioChartData: ChartData<'doughnut'> = {
    labels: ['Employees', 'Departments'],
    datasets: [{ data: [0, 0], label: 'Count' }]
  };

  leaveRequestsChartData: ChartData<'bar'> = {
    labels: [],
    datasets: [{
      label: 'Number of Requests',
      data: [],
      backgroundColor: [
        'rgba(75, 192, 192, 0.5)',
        'rgba(255, 99, 132, 0.5)',
        'rgba(255, 205, 86, 0.5)'
      ],
      borderColor: [
        'rgba(75, 192, 192, 1)',
        'rgba(255, 99, 132, 1)',
        'rgba(255, 205, 86, 1)'
      ],
      borderWidth: 1
    }]
  };

  doughnutChartOptions: ChartOptions<'doughnut'> = {
    responsive: true,
    plugins: {
      legend: {
        position: 'top',
      },
      tooltip: {
        callbacks: {
          label: function (context) {
            let label = context.dataset.label || '';
            if (label) {
              label += ': ';
            }
            if (context.parsed) {
              label += `${context.parsed}`;
            }
            return label;
          }
        }
      }
    }
  };

  barChartOptions: ChartOptions<'bar'> = {
    responsive: true,
    maintainAspectRatio: false,  // Add this
    scales: {
      y: {
        beginAtZero: true,
        ticks: {
          stepSize: 1
        }
      }
    },
    plugins: {
      tooltip: {
        callbacks: {
          label: (context) => {
            return `Count: ${context.parsed.y}`;
          }
        }
      }
    }
  };

  ngOnInit(): void {
    this.employeeService.getAllEmployees().subscribe(result => {
      this.numberOfEmployees = result.length;
      this.updateEmployeeToDepartmentRatio();
    });

    this.departmentService.getDepartments().subscribe(result => {
      this.numberOfDepartments = result.length;
      this.updateEmployeeToDepartmentRatio();
    });

    this.leaveRequestService.getAllLeaveRequests().subscribe(result => {
      const statusCounts = result.reduce((acc: any, leave: any) => {
        acc[leave.status] = (acc[leave.status] || 0) + 1;
        return acc;
      }, {});
      this.leaveRequestsByStatus = statusCounts;
      this.updateLeaveRequestsChart();
    });
  }

  updateEmployeeToDepartmentRatio(): void {
    const labels = ['Employees', 'Departments'];
    const data = [this.numberOfEmployees, this.numberOfDepartments];

    this.employeeToDepartmentRatioChartData = {
      labels,
      datasets: [{
        ...this.employeeToDepartmentRatioChartData.datasets[0],
        data
      }]
    };
  }

  updateLeaveRequestsChart(): void {
    const labels = Object.keys(this.leaveRequestsByStatus);
    const data = labels.map(key => this.leaveRequestsByStatus[key]);

    this.leaveRequestsChartData = {
      labels,
      datasets: [{
        ...this.leaveRequestsChartData.datasets[0],
        data
      }]
    };
  }
}
