import { Component, Input, OnInit } from '@angular/core';
import { LeaveRequestService } from 'src/app/services/leave-request.service';
import { LeaveRequest } from 'src/app/models/LeaveRequest';
import { formatDate } from '@angular/common';
import { SnackbarService } from 'src/app/components/snackbar/snackbar.service';

@Component({
  selector: 'app-leave-requests',
  templateUrl: './leave-requests.component.html',
  styleUrls: ['./leave-requests.component.scss']
})
export class LeaveRequestsComponent implements OnInit {
  @Input() status!: string;
  leaveRequests: LeaveRequest[] = [];
  currentDate: string = formatDate(new Date(), 'yyyy-MM-dd', 'en');

  constructor(private snackbarService: SnackbarService, private leaveRequestService: LeaveRequestService) { }

  ngOnInit(): void {
    this.leaveRequestService.getLeaveRequestsByStatus(this.status).subscribe(requests => {
      this.leaveRequests = requests;
    });
  }
  approveRequest(id: number): void {
    this.leaveRequestService.updateLeaveRequestStatus(id, 'APPROVED')
      .subscribe({
        next: () => {
          this.leaveRequests = this.leaveRequests.filter(r => r.id !== id);
          this.snackbarService.show('Leave request approved!');
        },
        error: (err) => this.snackbarService.show('Error updating request status')
      });
  }

  denyRequest(id: number): void {
    const denialReason = prompt("Reason for denial:");
    if (denialReason === null) return;

    this.leaveRequestService.updateLeaveRequestStatus(id, 'REJECTED', denialReason || '')
      .subscribe({
        next: () => {
          this.leaveRequests = this.leaveRequests.filter(r => r.id !== id);
          this.snackbarService.show('Leave request denied!');
        },
        error: (err) => this.snackbarService.show('Error updating request status')
      });
  }

  getStatusClass(status: string): string {
    switch (status) {
      case 'APPROVED':
        return 'status-approved';
      case 'REJECTED':
        return 'status-denied';
      case 'PENDING':
        return 'status-pending';
      default:
        return '';
    }
  }

}
