import React, { useState } from "react";
import { X, Send, AlertCircle } from "lucide-react";
import toast from "react-hot-toast";

const CATEGORIES = [
  { value: "ACADEMIC", label: "Academic / Coursework" },
  { value: "ADMINISTRATIVE", label: "Administrative / Records" },
  { value: "FINANCIAL", label: "Financial / Tuition" },
  { value: "TECHNICAL", label: "Technical / Platform" },
  { value: "GENERAL", label: "General Inquiry" },
];

const PRIORITIES = [
  { value: "LOW", label: "Low Priority" },
  { value: "MEDIUM", label: "Medium Priority" },
  { value: "HIGH", label: "High Priority" },
  { value: "URGENT", label: "Urgent" },
];

export default function NewQueryModal({ isOpen, onClose, onSubmit }) {
  const [subject, setSubject] = useState("");
  const [category, setCategory] = useState("ACADEMIC");
  const [priority, setPriority] = useState("MEDIUM");
  const [initialMessage, setInitialMessage] = useState("");
  const [loading, setLoading] = useState(false);

  if (!isOpen) return null;

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!subject.trim() || !initialMessage.trim()) {
      toast.error("Please fill in subject and message details");
      return;
    }

    try {
      setLoading(true);
      await onSubmit({
        subject,
        category,
        priority,
        initialMessage,
      });
      setSubject("");
      setInitialMessage("");
      onClose();
    } catch (err) {
      toast.error(err.response?.data?.message || "Failed to raise query");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center bg-slate-900/60 backdrop-blur-sm p-4">
      <div className="w-full max-w-xl bg-white dark:bg-slate-900 rounded-2xl shadow-2xl border border-slate-200 dark:border-slate-800 overflow-hidden animate-in fade-in zoom-in-95 duration-200">
        
        {/* Header */}
        <div className="flex items-center justify-between px-6 py-4 bg-gradient-to-r from-blue-600 to-indigo-600 text-white">
          <div className="flex items-center gap-2">
            <Send className="w-5 h-5" />
            <h3 className="font-semibold text-lg">Raise a Student Query</h3>
          </div>
          <button
            onClick={onClose}
            className="p-1 text-blue-100 hover:text-white rounded-lg hover:bg-white/10 transition-colors"
          >
            <X className="w-5 h-5" />
          </button>
        </div>

        {/* Form Body */}
        <form onSubmit={handleSubmit} className="p-6 space-y-4">
          <div>
            <label className="block text-xs font-semibold text-slate-700 dark:text-slate-300 uppercase tracking-wider mb-1">
              Subject Header *
            </label>
            <input
              type="text"
              placeholder="e.g. Issue with Midterm Grade Calculation in CS101"
              value={subject}
              onChange={(e) => setSubject(e.target.value)}
              className="w-full px-4 py-2.5 rounded-xl border border-slate-300 dark:border-slate-700 bg-slate-50 dark:bg-slate-800 text-slate-900 dark:text-white focus:outline-none focus:ring-2 focus:ring-blue-500"
              required
            />
          </div>

          <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
            <div>
              <label className="block text-xs font-semibold text-slate-700 dark:text-slate-300 uppercase tracking-wider mb-1">
                Category
              </label>
              <select
                value={category}
                onChange={(e) => setCategory(e.target.value)}
                className="w-full px-4 py-2.5 rounded-xl border border-slate-300 dark:border-slate-700 bg-slate-50 dark:bg-slate-800 text-slate-900 dark:text-white focus:outline-none focus:ring-2 focus:ring-blue-500"
              >
                {CATEGORIES.map((c) => (
                  <option key={c.value} value={c.value}>
                    {c.label}
                  </option>
                ))}
              </select>
            </div>

            <div>
              <label className="block text-xs font-semibold text-slate-700 dark:text-slate-300 uppercase tracking-wider mb-1">
                Priority
              </label>
              <select
                value={priority}
                onChange={(e) => setPriority(e.target.value)}
                className="w-full px-4 py-2.5 rounded-xl border border-slate-300 dark:border-slate-700 bg-slate-50 dark:bg-slate-800 text-slate-900 dark:text-white focus:outline-none focus:ring-2 focus:ring-blue-500"
              >
                {PRIORITIES.map((p) => (
                  <option key={p.value} value={p.value}>
                    {p.label}
                  </option>
                ))}
              </select>
            </div>
          </div>

          <div>
            <label className="block text-xs font-semibold text-slate-700 dark:text-slate-300 uppercase tracking-wider mb-1">
              Detailed Description / Query Message *
            </label>
            <textarea
              rows={4}
              placeholder="Describe your query in detail..."
              value={initialMessage}
              onChange={(e) => setInitialMessage(e.target.value)}
              className="w-full px-4 py-2.5 rounded-xl border border-slate-300 dark:border-slate-700 bg-slate-50 dark:bg-slate-800 text-slate-900 dark:text-white focus:outline-none focus:ring-2 focus:ring-blue-500"
              required
            />
          </div>

          <div className="flex items-center gap-2 text-xs text-slate-500 dark:text-slate-400 bg-blue-50 dark:bg-blue-950/40 p-3 rounded-xl border border-blue-100 dark:border-blue-900">
            <AlertCircle className="w-4 h-4 text-blue-600 flex-shrink-0" />
            <span>
              Submitting will publish an event to Kafka and notify assigned faculty/admin via WebSocket in real-time.
            </span>
          </div>

          <div className="flex justify-end gap-3 pt-3 border-t border-slate-100 dark:border-slate-800">
            <button
              type="button"
              onClick={onClose}
              className="px-5 py-2.5 rounded-xl font-medium text-slate-600 dark:text-slate-300 hover:bg-slate-100 dark:hover:bg-slate-800 transition-colors"
            >
              Cancel
            </button>
            <button
              type="submit"
              disabled={loading}
              className="px-6 py-2.5 rounded-xl font-medium text-white bg-blue-600 hover:bg-blue-700 transition-all shadow-md shadow-blue-500/20 disabled:opacity-50"
            >
              {loading ? "Publishing Event..." : "Send Query"}
            </button>
          </div>
        </form>
      </div>
    </div>
  );
}
