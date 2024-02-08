import { create } from "zustand";
import { persist } from "zustand/middleware";
// import jwt_decode from "jwt-decode";
import { IPostDetail, IReply, ISubReply, IPostDetailResponse, IEditPostRequest, IReplyRequest, IReplyResponse } from "./postdetailinterface";
import { formatDate } from "./util";

interface IPostDetailStore {
    postdetail: IPostDetail;
    jwtToken2: string;
    fetchPostDetail: () => Promise<IPostDetailResponse>;
    editPostDetail: (request: IEditPostRequest) => Promise<IPostDetailResponse>;
    publishPostDetail: (request: IEditPostRequest) => Promise<IPostDetailResponse>;
    savePostDetail: (request: IEditPostRequest) => Promise<IPostDetailResponse>;
    archivePost: () => Promise<IPostDetailResponse>;
    hidePost: () => Promise<IPostDetailResponse>;
    unhidePost: () => Promise<IPostDetailResponse>;
    deletePost: () => Promise<IPostDetailResponse>;
    submitReplyRequest: (request: IReplyRequest, replyId: string) => Promise<IReplyResponse>;
    deleteReplyRequest: (replyId: string) => Promise<IReplyResponse>;
}

// /post-reply-service
const domain = "http://localhost:8085";
const postdetailUrl = `${domain}/posts/65c03132b188bf4a7d73a2c7`;
const editPostUrl = `${domain}/posts/65c03132b188bf4a7d73a2c7/modify`;
const publishPostUrl = `${domain}/posts/publish`;
const savePostUrl = `${domain}/posts/save`;
const archivePostUrl = `${domain}/posts/65c03132b188bf4a7d73a2c7/archive`;
const hidePostUrl = `${domain}/posts/65c03132b188bf4a7d73a2c7/hide`;
const unhidePostUrl = `${domain}/posts/65c03132b188bf4a7d73a2c7/unhide`;
const deletePostUrl = `${domain}/posts/65c03132b188bf4a7d73a2c7/delete`;
const replyUrl = `${domain}/reply/65c03132b188bf4a7d73a2c7/reply`;
const deleteReplyUrl = `${domain}/reply/65c03132b188bf4a7d73a2c7/delete-reply/`;

export const usePostDetailStore = create<IPostDetailStore>()(
    persist(
        (set, get) => ({
            postdetail: {
                postId: "111",
                userId: 1,
                title: "MyTitle",
                dateCreated: "2024-01-01",
                content: "MyContent",
                dateModified: "2024-01-01",
                status: "MyStatus",
                isArchived: false as boolean,
                images: ["", ""],
                attachments: ["", ""],
                postReplies: []
            },

            jwtToken2: "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIyIiwicGVybWlzc2lvbnMiOlt7ImF1dGhvcml0eSI6IlJPTEVfVVNFUiJ9XX0.uiM5Llbx-FYb4rbwV33BR04lmpJpDP6Sq-uD73DWSxw",

            fetchPostDetail: async (): Promise<IPostDetailResponse> => {
                try {
                    const response = await fetch(postdetailUrl, {
                        method: 'GET',
                        headers: {
                            'Authorization': `Bearer ${get().jwtToken2}`,
                        }
                    });
                    if (!response.ok) {
                        throw new Error('Failed to fetch post detail');
                    }
                    const data: IPostDetailResponse = await response.json();
                    console.log(data);
                    // data.dateCreated = data.dateCreated.substring(0, 10);
                    // if (data.dateModified != null) {
                    //     data.dateModified = data.dateModified.substring(0, 10);
                    // }
                    set({ postdetail: data });
                    return data;
                }
                catch (error) {
                    throw new Error('Fail to fetch post detail');
                }
            },

            editPostDetail : async (request: IEditPostRequest): Promise<IPostDetailResponse> => {
                try {
                    const response = await fetch(editPostUrl, {
                        method: 'PATCH',
                        headers: {
                            'Content-Type': 'application/json',
                            'Authorization': `Bearer ${get().jwtToken2}`,
                        },
                        body: JSON.stringify(request),
                    });
                    if (!response.ok) {
                        throw new Error('Failed to fetch post detail');
                    }
                    const data: IPostDetailResponse = await response.json();
                    console.log(data);
                    data.dateCreated = formatDate(data.dateCreated);
                    if (data.dateModified != null) {
                        data.dateModified = formatDate(data.dateModified);
                    }
                    get().fetchPostDetail();
                    // set({ postdetail: data });
                    return data;
                }
                catch (error) {
                    throw new Error('Fail to fetch post detail');
                }
            },

            publishPostDetail: async (request: IEditPostRequest): Promise<IPostDetailResponse> => {
                try {
                    const response = await fetch(publishPostUrl, {
                        method: 'PUT',
                        headers: {
                            'Content-Type': 'application/json',
                            'Authorization': `Bearer ${get().jwtToken2}`,
                        },
                        body: JSON.stringify(request),
                    });
                    if (!response.ok) {
                        throw new Error('Failed to fetch post detail');
                    }
                    const data: IPostDetailResponse = await response.json();
                    console.log(data);
                    data.dateCreated = formatDate(data.dateCreated);
                    if (data.dateModified != null) {
                        data.dateModified = formatDate(data.dateModified);
                    }
                    get().fetchPostDetail();
                    // set({ postdetail: data });
                    return data;
                }
                catch (error) {
                    throw new Error('Fail to fetch post detail');
                }
            },

            savePostDetail: async (request: IEditPostRequest): Promise<IPostDetailResponse> => {
                try {
                    const response = await fetch(savePostUrl, {
                        method: 'PUT',
                        headers: {
                            'Content-Type': 'application/json',
                            'Authorization': `Bearer ${get().jwtToken2}`,
                        },
                        body: JSON.stringify(request),
                    });
                    if (!response.ok) {
                        throw new Error('Failed to fetch post detail');
                    }
                    const data: IPostDetailResponse = await response.json();
                    console.log(data);
                    data.dateCreated = formatDate(data.dateCreated);
                    if (data.dateModified != null) {
                        data.dateModified = formatDate(data.dateModified);
                    }
                    get().fetchPostDetail();
                    // set({ postdetail: data });
                    return data;
                }
                catch (error) {
                    throw new Error('Fail to fetch post detail');
                }
            },

            archivePost : async () : Promise<IPostDetailResponse> => {
                try {
                    const response = await fetch(archivePostUrl, {
                        method: 'PATCH',
                        headers: {
                            'Authorization': `Bearer ${get().jwtToken2}`,
                        }
                    });
                    if (!response.ok) {
                        throw new Error('Failed to delete the reply');
                    }
                    const data: IPostDetailResponse = await response.json();
                    console.log(data);
                    get().fetchPostDetail();
                    return data;
                }
                catch (error) {
                    throw new Error('Fail to delete the reply');
                }
            },

            hidePost: async () : Promise<IPostDetailResponse> => {
                try {
                    const response = await fetch(hidePostUrl, {
                        method: 'PATCH',
                        headers: {
                            'Authorization': `Bearer ${get().jwtToken2}`,
                        }
                    });
                    if (!response.ok) {
                        throw new Error('Failed to delete the reply');
                    }
                    const data: IPostDetailResponse = await response.json();
                    console.log(data);
                    get().fetchPostDetail();
                    return data;
                }
                catch (error) {
                    throw new Error('Fail to delete the reply');
                }
            },

            unhidePost: async () : Promise<IPostDetailResponse> => {
                try {
                    const response = await fetch(unhidePostUrl, {
                        method: 'PATCH',
                        headers: {
                            'Authorization': `Bearer ${get().jwtToken2}`,
                        }
                    });
                    if (!response.ok) {
                        throw new Error('Failed to delete the reply');
                    }
                    const data: IPostDetailResponse = await response.json();
                    console.log(data);
                    get().fetchPostDetail();
                    return data;
                }
                catch (error) {
                    throw new Error('Fail to delete the reply');
                }
            },

            deletePost: async () : Promise<IPostDetailResponse> => {
                try {
                    const response = await fetch(deletePostUrl, {
                        method: 'PATCH',
                        headers: {
                            'Authorization': `Bearer ${get().jwtToken2}`,
                        }
                    });
                    if (!response.ok) {
                        throw new Error('Failed to delete the reply');
                    }
                    const data: IPostDetailResponse = await response.json();
                    console.log(data);
                    get().fetchPostDetail();
                    return data;
                }
                catch (error) {
                    throw new Error('Fail to delete the reply');
                }
            },

            submitReplyRequest: async (request: IReplyRequest, replyId: string): Promise<IReplyResponse> => {
                try {
                    let currReplyUrl;
                    if (!replyId.includes('-')) {
                        currReplyUrl = replyUrl + "/?firstLayerReplyId=" + replyId;
                    }
                    else {
                        currReplyUrl = replyUrl;
                    }
                    const response = await fetch(currReplyUrl, {
                        method: 'PATCH',
                        headers: {
                            'Content-Type': 'application/json',
                            'Authorization': `Bearer ${get().jwtToken2}`,
                        },
                        body: JSON.stringify(request),
                    });
                    if (!response.ok) {
                        throw new Error('Fail to get response from the reply request');
                    }
                    const addedReply: IReplyResponse = await response.json();
                    // const originalPost: IPostDetail = get().postdetail;
                    // originalPost.postReplies = [...originalPost.postReplies, addedReply] as IReply;
                    get().fetchPostDetail();
                    return addedReply;
                }
                catch (error) {
                    throw new Error('Fail to fetch post detail');
                }
            },

            deleteReplyRequest: async (replyId: string): Promise<IReplyResponse> => {
                try {
                    const response = await fetch(deleteReplyUrl + replyId, {
                        method: 'PATCH',
                        headers: {
                            'Authorization': `Bearer ${get().jwtToken2}`,
                        }
                    });
                    if (!response.ok) {
                        throw new Error('Failed to delete the reply');
                    }
                    const data: IReplyResponse = await response.json();
                    console.log(data);
                    get().fetchPostDetail();
                    return data;
                }
                catch (error) {
                    throw new Error('Fail to delete the reply');
                }
            }
        }),
        {
            name: "post_detail",
        }
    )
);