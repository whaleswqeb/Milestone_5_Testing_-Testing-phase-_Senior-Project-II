import React, { useState, useRef, useEffect } from "react";
import { 
  Sparkles, X, Send, Bot, User, RefreshCw, 
  ChevronDown, HelpCircle, BookOpen, GraduationCap 
} from "lucide-react";
import toast from "react-hot-toast";
import { aiApi } from "../../api/aiApi";

const SUGGESTIONS = [
  "How is my GPA calculated?",
  "How to raise a query to faculty?",
  "What are course enrollment rules?",
  "How to plan my semester workload?",
];

export default function AiChatWidget() {
  const [isOpen, setIsOpen] = useState(false);
  const [messages, setMessages] = useState([
    {
      id: "welcome-1",
      sender: "ai",
      text: "Hello! I am your Spring AI Academic Assistant. Ask me anything about your GPA projections, course policies, or how to raise queries in the Mailbox!",
      model: "Spring AI Academic Model",
      time: new Date().toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' }),
    },
  ]);
  const [inputPrompt, setInputPrompt] = useState("");
  const [loading, setLoading] = useState(false);
  const chatEndRef = useRef(null);

  useEffect(() => {
    if (isOpen) {
      chatEndRef.current?.scrollIntoView({ behavior: "smooth" });
    }
  }, [messages, isOpen]);

  const handleSendMessage = async (promptText) => {
    const queryText = promptText || inputPrompt;
    if (!queryText.trim()) return;

    const userMsg = {
      id: Date.now().toString(),
      sender: "user",
      text: queryText,
      time: new Date().toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' }),
    };

    setMessages((prev) => [...prev, userMsg]);
    if (!promptText) setInputPrompt("");
    setLoading(true);

    try {
      const response = await aiApi.chat(queryText);
      const aiMsg = {
        id: (Date.now() + 1).toString(),
        sender: "ai",
        text: response.answer,
        model: response.modelUsed,
        time: new Date().toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' }),
      };
      setMessages((prev) => [...prev, aiMsg]);
    } catch (err) {
      toast.error("Spring AI service error. Please try again.");
      setMessages((prev) => [
        ...prev,
        {
          id: (Date.now() + 1).toString(),
          sender: "ai",
          text: "Apologies, I encountered an issue connecting to the Spring AI engine. Please check your network connection.",
          time: new Date().toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' }),
        },
      ]);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="fixed bottom-6 right-6 z-50">
      {/* Launcher Button */}
      {!isOpen && (
        <button
          onClick={() => setIsOpen(true)}
          className="group flex items-center gap-3 px-5 py-3.5 bg-gradient-to-r from-blue-600 via-indigo-600 to-purple-600 text-white font-semibold rounded-full shadow-2xl hover:shadow-blue-500/30 hover:scale-105 active:scale-95 transition-all duration-200"
        >
          <div className="p-1.5 bg-white/20 rounded-full animate-pulse">
            <Sparkles className="w-5 h-5 text-yellow-300" />
          </div>
          <span>Spring AI Assistant</span>
          <span className="flex h-2.5 w-2.5 relative">
            <span className="animate-ping absolute inline-flex h-full w-full rounded-full bg-emerald-400 opacity-75"></span>
            <span className="relative inline-flex rounded-full h-2.5 w-2.5 bg-emerald-500"></span>
          </span>
        </button>
      )}

      {/* Expanded Chat Drawer */}
      {isOpen && (
        <div className="w-[360px] sm:w-[420px] h-[580px] bg-white dark:bg-slate-900 rounded-3xl shadow-2xl border border-slate-200 dark:border-slate-800 flex flex-col overflow-hidden animate-in fade-in slide-in-from-bottom-5 duration-200">
          {/* Header */}
          <div className="px-5 py-4 bg-gradient-to-r from-blue-600 via-indigo-600 to-purple-600 text-white flex items-center justify-between">
            <div className="flex items-center gap-3">
              <div className="p-2 bg-white/20 rounded-xl backdrop-blur-md">
                <Bot className="w-6 h-6 text-white" />
              </div>
              <div>
                <h3 className="font-bold text-base flex items-center gap-1.5">
                  Spring AI Advisor
                  <Sparkles className="w-4 h-4 text-yellow-300 fill-yellow-300" />
                </h3>
                <p className="text-xs text-blue-100 flex items-center gap-1">
                  <span className="w-2 h-2 rounded-full bg-emerald-400 inline-block"></span>
                  Real-time Academic Intelligence
                </p>
              </div>
            </div>

            <button
              onClick={() => setIsOpen(false)}
              className="p-1.5 text-blue-100 hover:text-white rounded-lg hover:bg-white/10 transition-colors"
            >
              <ChevronDown className="w-5 h-5" />
            </button>
          </div>

          {/* Messages Container */}
          <div className="flex-1 p-4 overflow-y-auto space-y-3 bg-slate-50/50 dark:bg-slate-950/40">
            {messages.map((msg) => {
              const isAi = msg.sender === "ai";
              return (
                <div
                  key={msg.id}
                  className={`flex gap-2.5 ${isAi ? "flex-row" : "flex-row-reverse"}`}
                >
                  <div
                    className={`w-8 h-8 rounded-full flex items-center justify-center text-xs font-bold flex-shrink-0 ${
                      isAi
                        ? "bg-gradient-to-br from-blue-600 to-indigo-600 text-white"
                        : "bg-slate-700 text-white"
                    }`}
                  >
                    {isAi ? <Bot className="w-4 h-4" /> : <User className="w-4 h-4" />}
                  </div>

                  <div className="max-w-[80%] space-y-1">
                    <div
                      className={`p-3.5 rounded-2xl text-xs sm:text-sm leading-relaxed ${
                        isAi
                          ? "bg-white dark:bg-slate-900 text-slate-800 dark:text-slate-100 border border-slate-200 dark:border-slate-800 rounded-tl-none shadow-sm"
                          : "bg-blue-600 text-white rounded-tr-none shadow-md shadow-blue-500/10"
                      }`}
                    >
                      <p className="whitespace-pre-wrap">{msg.text}</p>
                    </div>

                    <div
                      className={`flex items-center gap-2 text-[10px] text-slate-400 px-1 ${
                        isAi ? "justify-start" : "justify-end"
                      }`}
                    >
                      {msg.model && (
                        <span className="font-semibold text-blue-600 dark:text-blue-400">
                          {msg.model}
                        </span>
                      )}
                      <span>{msg.time}</span>
                    </div>
                  </div>
                </div>
              );
            })}

            {loading && (
              <div className="flex gap-2.5">
                <div className="w-8 h-8 rounded-full bg-gradient-to-br from-blue-600 to-indigo-600 text-white flex items-center justify-center flex-shrink-0">
                  <Bot className="w-4 h-4" />
                </div>
                <div className="p-3 bg-white dark:bg-slate-900 rounded-2xl border border-slate-200 dark:border-slate-800 rounded-tl-none flex items-center gap-2">
                  <RefreshCw className="w-4 h-4 text-blue-600 animate-spin" />
                  <span className="text-xs text-slate-500 dark:text-slate-400">
                    Spring AI processing prompt...
                  </span>
                </div>
              </div>
            )}
            <div ref={chatEndRef} />
          </div>

          {/* Prompt Suggestions */}
          <div className="px-3 py-2 bg-white dark:bg-slate-900 border-t border-slate-100 dark:border-slate-800 flex items-center gap-1.5 overflow-x-auto no-scrollbar">
            {SUGGESTIONS.map((sug, idx) => (
              <button
                key={idx}
                onClick={() => handleSendMessage(sug)}
                className="px-2.5 py-1 rounded-full text-[11px] font-medium bg-slate-100 dark:bg-slate-800 text-slate-600 dark:text-slate-300 hover:bg-blue-50 hover:text-blue-600 dark:hover:bg-blue-950/50 dark:hover:text-blue-400 transition-colors whitespace-nowrap flex-shrink-0"
              >
                {sug}
              </button>
            ))}
          </div>

          {/* Form Input */}
          <form
            onSubmit={(e) => {
              e.preventDefault();
              handleSendMessage();
            }}
            className="p-3 bg-white dark:bg-slate-900 border-t border-slate-200 dark:border-slate-800 flex items-center gap-2"
          >
            <input
              type="text"
              placeholder="Ask Spring AI anything..."
              value={inputPrompt}
              onChange={(e) => setInputPrompt(e.target.value)}
              className="flex-1 px-4 py-2.5 rounded-xl border border-slate-200 dark:border-slate-700 bg-slate-50 dark:bg-slate-800 text-slate-900 dark:text-white text-xs sm:text-sm focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
            <button
              type="submit"
              disabled={loading || !inputPrompt.trim()}
              className="p-2.5 bg-blue-600 hover:bg-blue-700 text-white rounded-xl transition-all shadow-md shadow-blue-500/20 disabled:opacity-50"
            >
              <Send className="w-4 h-4" />
            </button>
          </form>
        </div>
      )}
    </div>
  );
}
