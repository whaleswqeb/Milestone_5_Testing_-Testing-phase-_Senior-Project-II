import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom'


import AuthLayout from './layouts/AuthLayout'
import DashboardLayout from './layouts/DashboardLayout'
import ProtectedRoute from './router/ProtectedRoute'
import RoleRoute from './router/RoleRoute'
import Login from './pages/auth/Login'
import Register from './pages/auth/Register'
import StudentDashboard from './pages/student/Dashboard'
import StudentCourses from './pages/student/Courses'
import StudentEnrollments from './pages/student/Enrollments'
import StudentGrades from './pages/student/Grades'
import StudentProfile from './pages/student/Profile'
import FacultyDashboard from './pages/faculty/Dashboard'
import FacultyMyCourses from './pages/faculty/MyCourses'
import FacultyGradeEntry from './pages/faculty/GradeEntry'
import FacultyCourseForm from './pages/faculty/CourseForm'
import FacultyProfile from './pages/faculty/Profile'
import AdminDashboard from './pages/admin/Dashboard'
import AdminUsers from './pages/admin/Users'
import AdminCourses from './pages/admin/Courses'
import AdminReports from './pages/admin/Reports'
import AdminCalendar from './pages/admin/Calendar'
import QueryList from './pages/mailbox/QueryList'
import QueryDetail from './pages/mailbox/QueryDetail'
import AiChatWidget from './components/ai/AiChatWidget'

import Notifications from './pages/Notifications'
import NotFound from './pages/NotFound'

const App = () => {
  return (
    <BrowserRouter>
      <Routes>

    
        <Route element={<AuthLayout />}>
          <Route path="/login"    element={<Login />} />
          <Route path="/register" element={<Register />} />
        </Route>
        <Route element={<ProtectedRoute />}>
          <Route element={<RoleRoute allowedRole="STUDENT" />}>
            <Route element={<DashboardLayout />}>
              <Route path="/student/dashboard"   element={<StudentDashboard />} />
              <Route path="/student/courses"     element={<StudentCourses />} />
              <Route path="/student/enrollments" element={<StudentEnrollments />} />
              <Route path="/student/grades"      element={<StudentGrades />} />
              <Route path="/student/profile"     element={<StudentProfile />} />
            </Route>
          </Route>
          <Route element={<RoleRoute allowedRole="FACULTY" />}>
            <Route element={<DashboardLayout />}>
              <Route path="/faculty/dashboard"        element={<FacultyDashboard />} />
              <Route path="/faculty/courses"          element={<FacultyMyCourses />} />
              <Route path="/faculty/courses/new"      element={<FacultyCourseForm />} />
              <Route path="/faculty/courses/:id/edit" element={<FacultyCourseForm />} />
              <Route path="/faculty/courses/:id/grades" element={<FacultyGradeEntry />} />
              <Route path="/faculty/profile" element={<FacultyProfile />} />
            </Route>
          </Route>
          <Route element={<RoleRoute allowedRole="ADMIN" />}>
            <Route element={<DashboardLayout />}>
              <Route path="/admin/dashboard" element={<AdminDashboard />} />
              <Route path="/admin/users"     element={<AdminUsers />} />
              <Route path="/admin/courses"   element={<AdminCourses />} />
              <Route path="/admin/reports"   element={<AdminReports />} />
              <Route path="/admin/calendar"  element={<AdminCalendar />} />
            </Route>
          </Route>
          <Route element={<DashboardLayout />}>
            <Route path="/mailbox" element={<QueryList />} />
            <Route path="/mailbox/:id" element={<QueryDetail />} />
            <Route path="/notifications" element={<Notifications />} />
          </Route>

        </Route>
        <Route path="/"  element={<Navigate to="/login" replace />} />
        <Route path="*"  element={<NotFound />} />

      </Routes>
      <AiChatWidget />
    </BrowserRouter>
  )
}

export default App