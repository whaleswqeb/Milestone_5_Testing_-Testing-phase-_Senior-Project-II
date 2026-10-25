import axiosInstance from "./axios";

export const aiApi = {
  chat: async (prompt, context = "") => {
    const res = await axiosInstance.post("/ai/chat", { prompt, context });
    return res.data.data;
  },
};
