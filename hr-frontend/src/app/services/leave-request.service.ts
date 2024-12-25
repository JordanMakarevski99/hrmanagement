import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { LeaveRequest } from '../models/LeaveRequest';

@Injectable({
  providedIn: 'root'
})
export class LeaveRequestService {

  private apiUrl = `${environment.apiUrl}/leave-requests`;

  constructor(private http: HttpClient) { }
  getAllLeaveRequests(): Observable<LeaveRequest[]> {
    return this.http.get<LeaveRequest[]>(`${this.apiUrl}`);
  }
  createLeaveRequest(leaveRequest: LeaveRequest): Observable<LeaveRequest> {
    return this.http.post<LeaveRequest>(`${this.apiUrl}`, leaveRequest);
  }

  getLeaveRequestsByEmployee(employeeId: number): Observable<LeaveRequest[]> {
    return this.http.get<LeaveRequest[]>(`${this.apiUrl}/${employeeId}`);
  }

  getLeaveRequestsByStatus(status: string): Observable<LeaveRequest[]> {
    return this.http.get<LeaveRequest[]>(`${this.apiUrl}/status?status=${status}`);
  }

  updateLeaveRequestStatus(id: number, status: string, reason?: string) {
    const body = {
      status: status,
      reason: reason
    };
    return this.http.put<LeaveRequest>(`${this.apiUrl}/${id}/status`, body);
  }
  deleteLeaveRequest(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
