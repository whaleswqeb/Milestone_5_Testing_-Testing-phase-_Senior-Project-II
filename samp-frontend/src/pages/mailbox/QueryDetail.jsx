import React, { useState, useEffect, useRef } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { useSelector } from "react-redux";
import { 
  ArrowLeft, Send, User, Clock, CheckCircle, 
  AlertCircle, MessageSquare, ShieldCheck, RefreshCw 
} from "lucide-react";
import toast from "react-hot-toast";
import { queryApi } from "../../api/queryApi";
import { useWebSocket } from "../../hooks/useWebSocket";

const STATUS_OPTIONS = ["OPEN", "IN_PROGRESS", "RESOLVED", "CLOSED"];

export default function QueryDetail() {
  const { id } = useParams();
  const navigate = useNavigate();
  const { user } = useSelector((state) => state.auth);
  const messagesEndRef = useRef(null);

  const [query, setQuery] = useState(null);
  const [loading, setLoading] = useState(true);
  const [newMessage, setNewMessage] = useState("");
  const [sending, setSending] = useState(false);
  const [updatingStatus, setUpdatingStatus] = useState(false);

  const isFacultyOrAdmin = user?.role === "FACULTY" || user?.role === "ADMIN";

  const fetchQueryDetail = async () => {
    try {
      setLoading(true);
      const data = await queryApi.getQueryById(id);
      setQuery(data);
    } catch (err) {
      toast.error("Failed to load query details");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchQueryDetail();
  }, [id]);

  useEffect(() => {
    messagesEndRef.current?.scrollIntoView({ behavior: "smooth" });
  }, [query?.messages]);

  // Real-time WebSocket event handler
  useWebSocket((event) => {
    if (event.queryId == id) {
      fetchQueryDetail();
    }
  });

  const handleSendMessage = async (e) => {
    e.preventDefault();
    if (!newMessage.trim()) return;

    try {
      setSending(true);
      const addedMsg = await queryApi.addMessage(id, newMessage);
      setQuery((prev) => ({
        ...prev,
        messages: [...(prev.messages || []), addedMsg],
      }));
      setNewMessage("");
      toast.success("Reply sent via event pipeline!");
    } catch (err) {
      toast.error(err.response?.data?.message || "Failed to send message");
    } finally {
      setSending(false);
    }
  };

  const handleStatusChange = async (newStatus) => {
    try {
      setUpdatingStatus(true);
      const updated = await queryApi.updateStatus(id, newStatus);
      setQuery(updated);
      toast.success(`Query status changed to ${newStatus}`);
    } catch (err) {
      toast.error("Failed to update status");
    } finally {
      setUpdatingStatus(false);
    }
  };

  if (loading) {
    return (
      <div className="flex flex-col items-center justify-center py-20 bg-white dark:bg-slate-900 rounded-2xl border border-slate-200 dark:border-slate-800">
        <RefreshCw className="w-8 h-8 text-blue-500 animate-spin mb-3" />
        <p className="text-slate-500 dark:text-slate-400 text-sm">Fetching query message thread...</p>
      </div>
    );
  }

  if (!query) {
    return (
      <div className="text-center py-16 bg-white dark:bg-slate-900 rounded-2xl border border-slate-200 dark:border-slate-800">
        <p className="text-slate-500 dark:text-slate-400 mb-4">Query not found or access denied.</p>
        <button
          onClick={() => navigate("/mailbox")}
          className="px-4 py-2 bg-blue-600 text-white rounded-xl text-sm font-medium"
        >
          Back to Mailbox
        </button>
      </div>
    );
  }

  return (
    <div className="space-y-6 max-w-5xl mx-auto">
      {/* Top Bar Navigation */}
      <button
        onClick={() => navigate("/mailbox")}
        className="flex items-center gap-2 text-sm font-medium text-slate-600 dark:text-slate-400 hover:text-blue-600 dark:hover:text-blue-400 transition-colors"
      >
        <ArrowLeft className="w-4 h-4" />
        <span>Back to Mailbox</span>
      </button>

      {/* Query Detail Header */}
      <div className="bg-white dark:bg-slate-900 p-6 rounded-2xl border border-slate-200 dark:border-slate-800 shadow-sm space-y-4">
        <div className="flex flex-col md:flex-row md:items-center justify-between gap-4">
          <div>
            <div className="flex items-center gap-2 mb-2 flex-wrap">
              <span className="px-2.5 py-1 rounded-full text-xs font-semibold bg-blue-500/10 text-blue-600 dark:text-blue-400 border border-blue-500/20">
                {query.category}
              </span>
              <span className="px-2.5 py-1 rounded-full text-xs font-semibold bg-amber-500/10 text-amber-600 dark:text-amber-400 border border-amber-500/20">
                {query.priority} Priority
              </span>
              <span className="px-2.5 py-1 rounded-full text-xs font-semibold bg-emerald-500/10 text-emerald-600 dark:text-emerald-400 border border-emerald-500/20">
                Status: {query.status.replace("_", " ")}
              </span>
            </div>

            <h1 className="text-2xl font-bold text-slate-900 dark:text-white">
              {query.subject}
            </h1>
          </div>

          {/* Status Change Dropdown for Faculty/Admin */}
          {isFacultyOrAdmin && (
            <div className="flex items-center gap-2">
              <label className="text-xs font-semibold text-slate-500 uppercase">
                Update Status:
              </label>
              <select
                value={query.status}
                disabled={updatingStatus}
                onChange={(e) => handleStatusChange(e.target.value)}
                className="px-3 py-2 rounded-xl text-sm font-medium border border-slate-300 dark:border-slate-700 bg-slate-50 dark:bg-slate-800 text-slate-900 dark:text-white focus:outline-none focus:ring-2 focus:ring-blue-500"
              >
                {STATUS_OPTIONS.map((st) => (
                  <option key={st} value={st}>
                    {st.replace("_", " ")}
                  </option>
                ))}
              </select>
            </div>
          )}
        </div>

        {/* Metadata Footer */}
        <div className="flex flex-wrap items-center gap-6 pt-3 border-t border-slate-100 dark:border-slate-800 text-xs text-slate-500 dark:text-slate-400">
          <div className="flex items-center gap-1.5">
            <User className="w-4 h-4 text-blue-500" />
            <span>Student: <strong className="text-slate-700 dark:text-slate-300">{query.studentName}</strong> ({query.studentEmail})</span>
          </div>
          <div className="flex items-center gap-1.5">
            <ShieldCheck className="w-4 h-4 text-emerald-500" />
            <span>Assigned: <strong className="text-slate-700 dark:text-slate-300">{query.assignedToName}</strong></span>
          </div>
          <div className="flex items-center gap-1.5">
            <Clock className="w-4 h-4 text-slate-400" />
            <span>Raised on: {new Date(query.createdAt).toLocaleString()}</span>
          </div>
        </div>
      </div>

      {/* Message Timeline */}
      <div className="bg-white dark:bg-slate-900 rounded-2xl border border-slate-200 dark:border-slate-800 shadow-sm overflow-hidden flex flex-col h-[520px]">
        {/* Messages Header */}
        <div className="px-6 py-3.5 bg-slate-50 dark:bg-slate-800/60 border-b border-slate-200 dark:border-slate-800 flex items-center justify-between">
          <div className="flex items-center gap-2 text-sm font-semibold text-slate-800 dark:text-slate-200">
            <MessageSquare className="w-4 h-4 text-blue-500" />
            <span>Threaded Discussion ({query.messages?.length || 0})</span>
          </div>
          <span className="text-xs text-slate-400">Real-time Kafka WebSocket pipeline active</span>
        </div>

        {/* Message Feed */}
        <div className="flex-1 p-6 overflow-y-auto space-y-4">
          {query.messages?.map((msg) => {
            const isMe = msg.senderId === user?.id;
            const isStaff = msg.senderRole === "FACULTY" || msg.senderRole === "ADMIN";

            return (
              <div
                key={msg.id}
                className={`flex gap-3 max-w-[85%] ${
                  isMe ? "ml-auto flex-row-reverse" : "mr-auto"
                }`}
              >
                {/* Avatar */}
                <div
                  className={`w-9 h-9 rounded-full flex items-center justify-center font-bold text-xs flex-shrink-0 ${
                    isStaff
                      ? "bg-purple-600 text-white"
                      : isMe
                      ? "bg-blue-600 text-white"
                      : "bg-slate-700 text-white"
                  }`}
                >
                  {msg.senderName?.charAt(0) || "U"}
                </div>

                {/* Bubble */}
                <div className="space-y-1">
                  <div className={`flex items-center gap-2 px-1 text-xs ${isMe ? "justify-end text-slate-400" : "text-slate-500"}`}>
                    <span className="font-semibold text-slate-700 dark:text-slate-300">
                      {msg.senderName}
                    </span>
                    {isStaff && (
                      <span className="px-1.5 py-0.5 rounded text-[10px] font-bold bg-purple-100 dark:bg-purple-950 text-purple-700 dark:text-purple-300">
                        {msg.senderRole}
                      </span>
                    )}
                    <span>• {new Date(msg.createdAt).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })}</span>
                  </div>

                  <div
                    className={`p-4 rounded-2xl text-sm leading-relaxed ${
                      isMe
                        ? "bg-blue-600 text-white rounded-tr-none shadow-md shadow-blue-500/10"
                        : isStaff
                        ? "bg-purple-50 dark:bg-purple-950/40 text-slate-900 dark:text-white border border-purple-200 dark:border-purple-900/60 rounded-tl-none"
                        : "bg-slate-100 dark:bg-slate-800 text-slate-900 dark:text-white border border-slate-200 dark:border-slate-700 rounded-tl-none"
                    }`}
                  >
                    <p className="whitespace-pre-wrap">{msg.message}</p>
                  </div>
                </div>
              </div>
            );
          })}
          <div ref={messagesEndRef} />
        </div>

        {/* Input Box */}
        <form
          onSubmit={handleSendMessage}
          className="p-4 bg-slate-50 dark:bg-slate-800/60 border-t border-slate-200 dark:border-slate-800 flex items-center gap-3"
        >
          <input
            type="text"
            placeholder="Type your response or update..."
            value={newMessage}
            onChange={(e) => setNewMessage(e.target.value)}
            className="flex-1 px-4 py-3 rounded-xl border border-slate-300 dark:border-slate-700 bg-white dark:bg-slate-900 text-slate-900 dark:text-white text-sm focus:outline-none focus:ring-2 focus:ring-blue-500"
          />
          <button
            type="submit"
            disabled={sending || !newMessage.trim()}
            className="px-5 py-3 bg-blue-600 hover:bg-blue-700 text-white font-medium rounded-xl transition-all shadow-md shadow-blue-500/20 disabled:opacity-50 flex items-center gap-2"
          >
            <Send className="w-4 h-4" />
            <span className="hidden sm:inline">Send</span>
          </button>
        </form>
      </div>
    </div>
  );
}
