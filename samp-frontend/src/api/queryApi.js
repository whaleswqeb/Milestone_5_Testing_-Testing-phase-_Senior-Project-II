import axiosInstance from "./axios";

export const queryApi = {
  getQueries: async () => {
    const res = await axiosInstance.get("/queries");
    return res.data.data;
  },

  getQueryById: async (queryId) => {
    const res = await axiosInstance.get(`/queries/${queryId}`);
    return res.data.data;
  },

  createQuery: async (data) => {
    const res = await axiosInstance.post("/queries", data);
    return res.data.data;
  },

  addMessage: async (queryId, message) => {
    const res = await axiosInstance.post(`/queries/${queryId}/messages`, { message });
    return res.data.data;
  },

  updateStatus: async (queryId, status) => {
    const res = await axiosInstance.patch(`/queries/${queryId}/status?status=${status}`);
    return res.data.data;
  },
};
