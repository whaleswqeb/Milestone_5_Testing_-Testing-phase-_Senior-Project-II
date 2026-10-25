import React, { useState, useEffect } from "react";
import { useQuery } from "@tanstack/react-query";
import { useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import {
  BookOpen, ClipboardList, Award,
  Bell, TrendingUp, ChevronRight,
  GraduationCap, Sliders, Inbox, Sparkles, RefreshCw
} from "lucide-react";
import {
  ResponsiveContainer, AreaChart, Area, XAxis, YAxis,
  Tooltip, PieChart, Pie, Cell, CartesianGrid
} from "recharts";
import { getStudentDashboardApi } from "../../api/studentApi";
import { selectUser } from "../../store/slices/authSlice";
import StatCard from "../../components/ui/StatCard";
import { PageLoader } from "../../components/ui/Spinner";
import { GradeBadge } from "../../components/ui/Badge";
import { formatGpa, timeAgo } from "../../utils/helpers";
import { useWebSocket } from "../../hooks/useWebSocket";

const GRADE_POINTS = {
  "A+": 4.0, "A": 4.0, "A-": 3.7,
  "B+": 3.3, "B": 3.0, "B-": 2.7,
  "C+": 2.3, "C": 2.0, "C-": 1.7,
  "D+": 1.3, "D": 1.0, "F": 0.0
};

const CHART_COLORS = ["#3b82f6", "#10b981", "#8b5cf6", "#f59e0b", "#ec4899"];

const NOTIF_ICONS = {
  GRADE_POSTED:         { icon: Award,        bg: "bg-emerald-50", color: "text-emerald-600" },
  GRADE_UPDATED:        { icon: TrendingUp,   bg: "bg-blue-50",    color: "text-blue-600"    },
  ENROLLMENT_CONFIRMED: { icon: ClipboardList, bg: "bg-indigo-50",  color: "text-indigo-600"  },
  COURSE_DROPPED:       { icon: BookOpen,     bg: "bg-red-50",     color: "text-red-600"     },
  GENERAL:              { icon: Bell,         bg: "bg-slate-100",  color: "text-slate-500"   },
};

const StudentDashboard = () => {
  const navigate = useNavigate();
  const user = useSelector(selectUser);

  const { data, isLoading, refetch } = useQuery({
    queryKey: ["studentDashboard"],
    queryFn: () => getStudentDashboardApi().then((r) => r.data.data),
  });

  // Listen for real-time WebSocket events to update dashboard stats live
  useWebSocket(() => {
    refetch();
  });

  const gpa = data?.gpa ?? 0;
  const activeEnrollments = data?.activeEnrollments ?? 0;
  const totalCreditsEarned = data?.totalCreditsEarned ?? 0;
  const unread = data?.unreadNotifications ?? 0;
  const notifications = data?.recentNotifications ?? [];
  const currentEnrollments = data?.currentEnrollments ?? [];

  // Interactive GPA Simulator state
  const [targetGrades, setTargetGrades] = useState({});

  useEffect(() => {
    if (currentEnrollments.length > 0) {
      const initial = {};
      currentEnrollments.forEach((e) => {
        initial[e.id] = e.grade || "A";
      });
      setTargetGrades(initial);
    }
  }, [currentEnrollments]);

  // Calculate simulated GPA
  const simulatedGpa = () => {
    if (!currentEnrollments.length) return gpa;
    let totalPoints = 0;
    let totalCredits = 0;
    currentEnrollments.forEach((e) => {
      const grade = targetGrades[e.id] || "A";
      const credits = e.credits || 3;
      const pts = GRADE_POINTS[grade] !== undefined ? GRADE_POINTS[grade] : 3.0;
      totalPoints += pts * credits;
      totalCredits += credits;
    });
    return totalCredits > 0 ? (totalPoints / totalCredits).toFixed(2) : gpa;
  };

  if (isLoading) return <PageLoader />;

  // Mock trend data for Recharts area chart
  const gpaTrendData = [
    { semester: "Sem 1", gpa: 3.2 },
    { semester: "Sem 2", gpa: 3.4 },
    { semester: "Sem 3", gpa: 3.5 },
    { semester: "Sem 4", gpa: 3.65 },
    { semester: "Current (Simulated)", gpa: parseFloat(simulatedGpa()) },
  ];

  // Distribution chart data
  const pieData = currentEnrollments.map((e, idx) => ({
    name: e.courseCode || `Course ${idx + 1}`,
    value: e.credits || 3,
  }));

  const getGpaColor = (g) =>
    g >= 3.5 ? "text-emerald-600" :
    g >= 2.5 ? "text-blue-600" :
    g >= 1.5 ? "text-amber-600" : "text-red-600";

  return (
    <div className="space-y-6">
      {/* Welcome Banner */}
      <div className="bg-gradient-to-r from-blue-600 via-indigo-600 to-purple-600 rounded-2xl p-6 text-white shadow-xl shadow-blue-500/10">
        <div className="flex items-center justify-between flex-wrap gap-4">
          <div>
            <p className="text-blue-100 text-sm font-medium">Welcome back,</p>
            <h1 className="text-2xl font-bold mt-0.5">{user?.fullName}</h1>
            <p className="text-blue-200 text-sm mt-1">
              {data?.major && `${data.major} · `}
              {data?.studentNumber && `ID: ${data.studentNumber}`}
            </p>
          </div>
          <div className="w-16 h-16 bg-white/10 backdrop-blur-md rounded-2xl flex items-center justify-center border border-white/20">
            <GraduationCap className="w-9 h-9 text-white" />
          </div>
        </div>
      </div>

      {/* Stat Cards */}
      <div className="grid grid-cols-2 lg:grid-cols-4 gap-4">
        <StatCard
          title="Current GPA"
          value={<span className={`text-3xl font-bold ${getGpaColor(gpa)}`}>{formatGpa(gpa)}</span>}
          subtitle="out of 4.00"
          icon={TrendingUp}
          iconBg="bg-emerald-50"
          iconColor="text-emerald-600"
        />
        <StatCard
          title="Active Courses"
          value={activeEnrollments}
          subtitle="enrolled this term"
          icon={BookOpen}
          iconBg="bg-blue-50"
          iconColor="text-blue-600"
        />
        <StatCard
          title="Credits Earned"
          value={totalCreditsEarned}
          subtitle="total credits"
          icon={Award}
          iconBg="bg-purple-50"
          iconColor="text-purple-600"
        />
        <StatCard
          title="Notifications"
          value={unread}
          subtitle="unread events"
          icon={Bell}
          iconBg="bg-amber-50"
          iconColor="text-amber-600"
        />
      </div>

      {/* Real-time Monitoring & Analytics Charts Row */}
      <div className="grid grid-cols-1 lg:grid-cols-3 gap-6">
        {/* GPA Progression & Trend Chart */}
        <div className="lg:col-span-2 bg-white dark:bg-slate-900 p-6 rounded-2xl border border-slate-200 dark:border-slate-800 shadow-sm">
          <div className="flex items-center justify-between mb-4">
            <div>
              <h2 className="text-base font-semibold text-slate-900 dark:text-white flex items-center gap-2">
                <TrendingUp className="w-5 h-5 text-blue-500" />
                Real-Time GPA Trajectory & Projections
              </h2>
              <p className="text-xs text-slate-500 dark:text-slate-400">
                Interactive real-time GPA trend calculated across terms
              </p>
            </div>
            <div className="px-3 py-1 bg-emerald-50 dark:bg-emerald-950/40 border border-emerald-200 dark:border-emerald-800 rounded-full text-xs font-semibold text-emerald-700 dark:text-emerald-300">
              Simulated GPA: {simulatedGpa()}
            </div>
          </div>

          <div className="h-64 w-full">
            <ResponsiveContainer width="100%" height="100%">
              <AreaChart data={gpaTrendData} margin={{ top: 10, right: 20, left: -20, bottom: 0 }}>
                <defs>
                  <linearGradient id="colorGpa" x1="0" y1="0" x2="0" y2="1">
                    <stop offset="5%" stopColor="#3b82f6" stopOpacity={0.4} />
                    <stop offset="95%" stopColor="#3b82f6" stopOpacity={0} />
                  </linearGradient>
                </defs>
                <CartesianGrid strokeDasharray="3 3" vertical={false} stroke="#e2e8f0" />
                <XAxis dataKey="semester" stroke="#94a3b8" fontSize={12} />
                <YAxis domain={[0, 4]} stroke="#94a3b8" fontSize={12} />
                <Tooltip
                  contentStyle={{
                    backgroundColor: "#0f172a",
                    borderColor: "#1e293b",
                    borderRadius: "12px",
                    color: "#fff",
                  }}
                />
                <Area type="monotone" dataKey="gpa" stroke="#3b82f6" strokeWidth={3} fillOpacity={1} fill="url(#colorGpa)" />
              </AreaChart>
            </ResponsiveContainer>
          </div>
        </div>

        {/* Credit Breakdown Pie Chart */}
        <div className="bg-white dark:bg-slate-900 p-6 rounded-2xl border border-slate-200 dark:border-slate-800 shadow-sm flex flex-col justify-between">
          <div>
            <h2 className="text-base font-semibold text-slate-900 dark:text-white mb-1">
              Workload & Credit Weight
            </h2>
            <p className="text-xs text-slate-500 dark:text-slate-400 mb-4">
              Current enrolled course credit breakdown
            </p>

            <div className="h-44 w-full flex items-center justify-center">
              {pieData.length > 0 ? (
                <ResponsiveContainer width="100%" height="100%">
                  <PieChart>
                    <Pie data={pieData} innerRadius={45} outerRadius={65} paddingAngle={5} dataKey="value">
                      {pieData.map((entry, index) => (
                        <Cell key={`cell-${index}`} fill={CHART_COLORS[index % CHART_COLORS.length]} />
                      ))}
                    </Pie>
                    <Tooltip />
                  </PieChart>
                </ResponsiveContainer>
              ) : (
                <p className="text-xs text-slate-400">No course data</p>
              )}
            </div>
          </div>

          <div className="space-y-1.5 pt-3 border-t border-slate-100 dark:border-slate-800 text-xs">
            {currentEnrollments.slice(0, 3).map((e, idx) => (
              <div key={e.id} className="flex items-center justify-between">
                <div className="flex items-center gap-2">
                  <span className="w-2.5 h-2.5 rounded-full" style={{ backgroundColor: CHART_COLORS[idx % CHART_COLORS.length] }}></span>
                  <span className="font-medium text-slate-700 dark:text-slate-300">{e.courseCode}</span>
                </div>
                <span className="text-slate-500">{e.credits || 3} credits</span>
              </div>
            ))}
          </div>
        </div>
      </div>

      {/* Interactive Real-Time GPA Predictor / Manipulator Tool */}
      <div className="bg-white dark:bg-slate-900 p-6 rounded-2xl border border-slate-200 dark:border-slate-800 shadow-sm">
        <div className="flex items-center justify-between mb-4">
          <div className="flex items-center gap-2">
            <div className="p-2.5 bg-purple-500/10 text-purple-600 dark:text-purple-400 rounded-xl">
              <Sliders className="w-5 h-5" />
            </div>
            <div>
              <h2 className="text-base font-bold text-slate-900 dark:text-white">
                Interactive GPA Predictor & Simulator
              </h2>
              <p className="text-xs text-slate-500 dark:text-slate-400">
                Adjust expected course letter grades below to manipulate and recalculate your term GPA in real time
              </p>
            </div>
          </div>
          <div className="text-right">
            <span className="text-xs text-slate-400">Projected Term GPA</span>
            <div className="text-xl font-extrabold text-blue-600 dark:text-blue-400">{simulatedGpa()}</div>
          </div>
        </div>

        {currentEnrollments.length === 0 ? (
          <p className="text-xs text-slate-400 py-4 text-center">Enroll in courses to enable real-time GPA manipulation.</p>
        ) : (
          <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-4">
            {currentEnrollments.map((e) => (
              <div key={e.id} className="p-4 rounded-xl bg-slate-50 dark:bg-slate-800/60 border border-slate-200 dark:border-slate-700/60">
                <div className="flex justify-between items-start mb-2">
                  <div>
                    <h4 className="font-semibold text-sm text-slate-900 dark:text-white">{e.courseCode}</h4>
                    <p className="text-xs text-slate-500 dark:text-slate-400">{e.courseName}</p>
                  </div>
                  <span className="text-xs text-slate-400 font-medium">{e.credits || 3} cr</span>
                </div>

                <div className="flex items-center gap-2 mt-3">
                  <label className="text-xs text-slate-500 font-semibold">Simulated Grade:</label>
                  <select
                    value={targetGrades[e.id] || "A"}
                    onChange={(ev) => setTargetGrades({ ...targetGrades, [e.id]: ev.target.value })}
                    className="flex-1 px-3 py-1.5 text-xs font-bold rounded-lg border border-slate-300 dark:border-slate-600 bg-white dark:bg-slate-900 text-slate-900 dark:text-white focus:ring-2 focus:ring-purple-500"
                  >
                    {Object.keys(GRADE_POINTS).map((g) => (
                      <option key={g} value={g}>
                        {g} ({GRADE_POINTS[g].toFixed(1)} pts)
                      </option>
                    ))}
                  </select>
                </div>
              </div>
            ))}
          </div>
        )}
      </div>

      {/* Bottom Grid: Current Enrollments & Recent Notifications */}
      <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
        {/* Current enrollments */}
        <div className="card">
          <div className="flex items-center justify-between mb-4">
            <h2 className="text-base font-semibold text-slate-900 dark:text-white">
              Current Enrollments
            </h2>
            <button
              onClick={() => navigate("/student/enrollments")}
              className="text-xs text-blue-600 font-medium flex items-center gap-1 hover:text-blue-700"
            >
              View all <ChevronRight className="w-3 h-3" />
            </button>
          </div>

          {currentEnrollments.length === 0 ? (
            <div className="flex flex-col items-center py-8 text-center">
              <BookOpen className="w-10 h-10 text-slate-200 dark:text-slate-700 mb-2" />
              <p className="text-sm text-slate-400">No active enrollments</p>
              <button
                onClick={() => navigate("/student/courses")}
                className="mt-3 text-xs text-blue-600 font-medium hover:underline"
              >
                Browse courses →
              </button>
            </div>
          ) : (
            <div className="space-y-2">
              {currentEnrollments.slice(0, 5).map((e) => (
                <div
                  key={e.id}
                  className="flex items-center justify-between p-3 rounded-xl bg-slate-50 dark:bg-slate-800/60 hover:bg-slate-100 dark:hover:bg-slate-800 transition-colors"
                >
                  <div className="min-w-0">
                    <p className="text-sm font-medium text-slate-900 dark:text-white truncate">
                      {e.courseName}
                    </p>
                    <p className="text-xs text-slate-400 mt-0.5">
                      {e.courseCode} · {e.facultyName}
                    </p>
                  </div>
                  <div className="flex items-center gap-2 shrink-0 ml-3">
                    <span className="text-xs text-slate-400">{e.credits} cr</span>
                    <GradeBadge grade={e.grade} />
                  </div>
                </div>
              ))}
            </div>
          )}
        </div>

        {/* Mailbox Query Center & Real-time Notifications */}
        <div className="card">
          <div className="flex items-center justify-between mb-4">
            <h2 className="text-base font-semibold text-slate-900 dark:text-white flex items-center gap-2">
              <Inbox className="w-4 h-4 text-blue-500" />
              Mailbox & Kafka Event Stream
            </h2>
            <button
              onClick={() => navigate("/mailbox")}
              className="text-xs text-blue-600 font-medium flex items-center gap-1 hover:text-blue-700"
            >
              Open Mailbox <ChevronRight className="w-3 h-3" />
            </button>
          </div>

          {notifications.length === 0 ? (
            <div className="flex flex-col items-center py-8 text-center">
              <Bell className="w-10 h-10 text-slate-200 dark:text-slate-700 mb-2" />
              <p className="text-sm text-slate-400">No events in notification stream</p>
            </div>
          ) : (
            <div className="space-y-2">
              {notifications.map((n) => {
                const config = NOTIF_ICONS[n.type] || NOTIF_ICONS.GENERAL;
                const Icon = config.icon;
                return (
                  <div
                    key={n.id}
                    className={`flex items-start gap-3 p-3 rounded-xl transition-colors ${
                      !n.read
                        ? "bg-blue-50 dark:bg-blue-950/40 border border-blue-100 dark:border-blue-900"
                        : "bg-slate-50 dark:bg-slate-800/60"
                    }`}
                  >
                    <div className={`w-8 h-8 ${config.bg} rounded-lg flex items-center justify-center shrink-0`}>
                      <Icon className={`w-4 h-4 ${config.color}`} />
                    </div>
                    <div className="flex-1 min-w-0">
                      <p className="text-sm text-slate-700 dark:text-slate-200 leading-snug">
                        {n.message}
                      </p>
                      <p className="text-xs text-slate-400 mt-1">
                        {timeAgo(n.createdAt)}
                      </p>
                    </div>
                    {!n.read && <div className="w-2 h-2 bg-blue-500 rounded-full shrink-0 mt-1" />}
                  </div>
                );
              })}
            </div>
          )}
        </div>
      </div>
    </div>
  );
};

export default StudentDashboard;