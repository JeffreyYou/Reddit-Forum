export interface IPostDetail {
    postId: string;
    userId: number;
    title: string;
    dateCreated: string;
    content: string;
    dateModified: string;
    status: string;
    isArchived: boolean;
    images: string[];
    attachments: string[];
    postReplies: IReply[];
}

export interface IPostDetailResponse {
    postId: string;
    userId: number;
    title: string;
    dateCreated: string;
    content: string;
    dateModified: string;
    status: string;
    isArchived: boolean;
    images: string[];
    attachments: string[];
    postReplies: IReply[];
}

export interface IReply {
    replyId: string;
    userId: number;
    postId: string;
    comment: string;
    isActivate: boolean;
    dateCreated: string;
    subReplies: ISubReply[];
}

export interface ISubReply {
    replyId: string;
    userId: number;
    postId: string;
    comment: string;
    isActivate: boolean;
    dateCreated: string;
}

export interface IEditPostRequest {
    postId: string | null;
    title: string;
    content: string;
    images: string[];
    attachments: string[];
}

export interface IReplyRequest {
    comment: string;
}

export interface IReplyResponse {
    responseStatus: {
        message: string;
        success: boolean;
    };
    comment: string;
    isActive: boolean;
}    
