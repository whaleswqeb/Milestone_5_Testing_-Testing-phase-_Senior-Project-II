import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { useSelector } from "react-redux";
import { 
  Inbox, Plus, Search, Filter, MessageSquare, 
  Clock, User, AlertCircle, CheckCircle2, RefreshCw 
} from "lucide-react";
import toast from "react-hot-toast";
import { queryApi } from "../../api/queryApi";
import NewQueryModal from "./NewQueryModal";
import { useWebSocket } from "../../hooks/useWebSocket";

const STATUS_OPTIONS = ["ALL", "OPEN", "IN_PROGRESS", "RESOLVED", "CLOSED"];

export default function QueryList() {
  const navigate = useNavigate();
  const { user } = useSelector((state) => state.auth);
  const isStudent = user?.role === "STUDENT";

  const [queries, setQueries] = useState([]);
  const [loading, setLoading] = useState(true);
  const [activeStatus, setActiveStatus] = useState("ALL");
  const [searchQuery, setSearchQuery] = useState("");
  const [isModalOpen, setIsModalOpen] = useState(false);

  const fetchQueries = async () => {
    try {
      setLoading(true);
      const data = await queryApi.getQueries();
      setQueries(data || []);
    } catch (err) {
      toast.error("Failed to load queries from server");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchQueries();
  }, []);

  // Subscribe to real-time WebSocket query events
  useWebSocket(() => {
    fetchQueries();
  });

  const handleCreateQuery = async (queryData) => {
    const newQuery = await queryApi.createQuery(queryData);
    toast.success("Query raised successfully!");
    setQueries((prev) => [newQuery, ...prev]);
  };

  const filteredQueries = queries.filter((q) => {
    const matchesStatus = activeStatus === "ALL" || q.status === activeStatus;
    const matchesSearch =
      q.subject?.toLowerCase().includes(searchQuery.toLowerCase()) ||
      q.studentName?.toLowerCase().includes(searchQuery.toLowerCase()) ||
      q.category?.toLowerCase().includes(searchQuery.toLowerCase());
    return matchesStatus && matchesSearch;
  });

  const getPriorityBadge = (priority) => {
    switch (priority) {
      case "URGENT":
        return "bg-rose-100 dark:bg-rose-950/60 text-rose-700 dark:text-rose-300 border-rose-200 dark:border-rose-900";
      case "HIGH":
        return "bg-amber-100 dark:bg-amber-950/60 text-amber-700 dark:text-amber-300 border-amber-200 dark:border-amber-900";
      case "MEDIUM":
        return "bg-blue-100 dark:bg-blue-950/60 text-blue-700 dark:text-blue-300 border-blue-200 dark:border-blue-900";
      default:
        return "bg-slate-100 dark:bg-slate-800 text-slate-700 dark:text-slate-300 border-slate-200 dark:border-slate-700";
    }
  };

  const getStatusBadge = (status) => {
    switch (status) {
      case "OPEN":
        return "bg-blue-500/10 text-blue-600 dark:text-blue-400 border-blue-500/20";
      case "IN_PROGRESS":
        return "bg-amber-500/10 text-amber-600 dark:text-amber-400 border-amber-500/20";
      case "RESOLVED":
        return "bg-emerald-500/10 text-emerald-600 dark:text-emerald-400 border-emerald-500/20";
      case "CLOSED":
        return "bg-slate-500/10 text-slate-600 dark:text-slate-400 border-slate-500/20";
      default:
        return "bg-slate-100 text-slate-700";
    }
  };

  return (
    <div className="space-y-6">
      {/* Header */}
      <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-4 bg-white dark:bg-slate-900 p-6 rounded-2xl border border-slate-200 dark:border-slate-800 shadow-sm">
        <div>
          <div className="flex items-center gap-3">
            <div className="p-3 bg-blue-500/10 text-blue-600 dark:text-blue-400 rounded-xl">
              <Inbox className="w-6 h-6" />
            </div>
            <div>
              <h1 className="text-2xl font-bold text-slate-900 dark:text-white">
                Mailbox & Student Query Center
              </h1>
              <p className="text-sm text-slate-500 dark:text-slate-400">
                {isStudent
                  ? "Raise queries to faculty/admin and track real-time responses"
                  : "Review and respond to incoming student academic queries in real-time"}
              </p>
            </div>
          </div>
        </div>

        <div className="flex items-center gap-3">
          <button
            onClick={fetchQueries}
            className="p-2.5 rounded-xl border border-slate-200 dark:border-slate-800 text-slate-600 dark:text-slate-300 hover:bg-slate-50 dark:hover:bg-slate-800 transition-colors"
            title="Refresh Queries"
          >
            <RefreshCw className={`w-5 h-5 ${loading ? "animate-spin" : ""}`} />
          </button>
          
          {isStudent && (
            <button
              onClick={() => setIsModalOpen(true)}
              className="flex items-center gap-2 px-5 py-2.5 bg-blue-600 hover:bg-blue-700 text-white font-medium rounded-xl shadow-lg shadow-blue-500/20 transition-all"
            >
              <Plus className="w-5 h-5" />
              <span>Raise Query</span>
            </button>
          )}
        </div>
      </div>

      {/* Filter Tabs & Search Bar */}
      <div className="flex flex-col md:flex-row md:items-center justify-between gap-4">
        {/* Status Filter Tabs */}
        <div className="flex items-center gap-1 overflow-x-auto bg-slate-100 dark:bg-slate-800/80 p-1.5 rounded-xl border border-slate-200 dark:border-slate-700/60">
          {STATUS_OPTIONS.map((status) => (
            <button
              key={status}
              onClick={() => setActiveStatus(status)}
              className={`px-4 py-2 rounded-lg text-xs font-semibold uppercase tracking-wider transition-all whitespace-nowrap ${
                activeStatus === status
                  ? "bg-white dark:bg-slate-900 text-blue-600 dark:text-blue-400 shadow-sm"
                  : "text-slate-600 dark:text-slate-400 hover:text-slate-900 dark:hover:text-white"
              }`}
            >
              {status.replace("_", " ")}
            </button>
          ))}
        </div>

        {/* Search Bar */}
        <div className="relative w-full md:w-72">
          <Search className="w-4 h-4 absolute left-3.5 top-1/2 -translate-y-1/2 text-slate-400" />
          <input
            type="text"
            placeholder="Search by subject, student, category..."
            value={searchQuery}
            onChange={(e) => setSearchQuery(e.target.value)}
            className="w-full pl-10 pr-4 py-2 rounded-xl border border-slate-200 dark:border-slate-800 bg-white dark:bg-slate-900 text-sm text-slate-900 dark:text-white focus:outline-none focus:ring-2 focus:ring-blue-500"
          />
        </div>
      </div>

      {/* Query Cards / List */}
      {loading ? (
        <div className="flex flex-col items-center justify-center py-16 bg-white dark:bg-slate-900 rounded-2xl border border-slate-200 dark:border-slate-800">
          <RefreshCw className="w-8 h-8 text-blue-500 animate-spin mb-3" />
          <p className="text-slate-500 dark:text-slate-400 text-sm">Loading queries from event hub...</p>
        </div>
      ) : filteredQueries.length === 0 ? (
        <div className="flex flex-col items-center justify-center py-16 bg-white dark:bg-slate-900 rounded-2xl border border-slate-200 dark:border-slate-800 text-center px-4">
          <div className="p-4 bg-slate-100 dark:bg-slate-800 rounded-2xl mb-4">
            <Inbox className="w-10 h-10 text-slate-400" />
          </div>
          <h3 className="text-lg font-semibold text-slate-800 dark:text-slate-200 mb-1">
            No queries found
          </h3>
          <p className="text-sm text-slate-500 dark:text-slate-400 max-w-sm mb-6">
            {searchQuery || activeStatus !== "ALL"
              ? "No queries match your current search or status filter."
              : isStudent
              ? "You haven't raised any queries yet. Click 'Raise Query' to contact faculty or administrative staff."
              : "No student queries assigned to your queue at this time."}
          </p>
          {isStudent && (
            <button
              onClick={() => setIsModalOpen(true)}
              className="px-5 py-2.5 bg-blue-600 text-white font-medium rounded-xl hover:bg-blue-700 transition-colors shadow-md shadow-blue-500/20"
            >
              Raise a Query Now
            </button>
          )}
        </div>
      ) : (
        <div className="grid grid-cols-1 gap-4">
          {filteredQueries.map((q) => (
            <div
              key={q.id}
              onClick={() => navigate(`/mailbox/${q.id}`)}
              className="group bg-white dark:bg-slate-900 p-5 rounded-2xl border border-slate-200 dark:border-slate-800 hover:border-blue-400 dark:hover:border-blue-500/50 hover:shadow-lg hover:shadow-blue-500/5 transition-all cursor-pointer"
            >
              <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-3 mb-3">
                <div className="flex items-center gap-2 flex-wrap">
                  <span className={`px-2.5 py-1 rounded-full text-xs font-semibold border ${getStatusBadge(q.status)}`}>
                    {q.status.replace("_", " ")}
                  </span>
                  <span className={`px-2.5 py-1 rounded-full text-xs font-semibold border ${getPriorityBadge(q.priority)}`}>
                    {q.priority}
                  </span>
                  <span className="px-2.5 py-1 rounded-full text-xs font-medium bg-slate-100 dark:bg-slate-800 text-slate-600 dark:text-slate-300">
                    {q.category}
                  </span>
                </div>

                <div className="flex items-center gap-2 text-xs text-slate-400 dark:text-slate-500">
                  <Clock className="w-3.5 h-3.5" />
                  <span>{new Date(q.createdAt).toLocaleString()}</span>
                </div>
              </div>

              <h3 className="text-base font-semibold text-slate-900 dark:text-white group-hover:text-blue-600 dark:group-hover:text-blue-400 transition-colors mb-2">
                {q.subject}
              </h3>

              <div className="flex items-center justify-between text-xs text-slate-500 dark:text-slate-400 pt-3 border-t border-slate-100 dark:border-slate-800/80">
                <div className="flex items-center gap-2">
                  <User className="w-3.5 h-3.5 text-slate-400" />
                  <span>
                    Student: <strong className="text-slate-700 dark:text-slate-200">{q.studentName}</strong>
                  </span>
                  <span className="mx-1">•</span>
                  <span>
                    Assigned: <strong className="text-slate-700 dark:text-slate-200">{q.assignedToName}</strong>
                  </span>
                </div>

                <div className="flex items-center gap-1.5 text-blue-600 dark:text-blue-400 font-medium">
                  <MessageSquare className="w-4 h-4" />
                  <span>{q.messages?.length || 0} messages</span>
                </div>
              </div>
            </div>
          ))}
        </div>
      )}

      {/* New Query Modal */}
      <NewQueryModal
        isOpen={isModalOpen}
        onClose={() => setIsModalOpen(false)}
        onSubmit={handleCreateQuery}
      />
    </div>
  );
}
