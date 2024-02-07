export interface IUserProfile {
    firstName: string;
    lastName: string;
    email: string;
    active: boolean;
    dateJoined: string;
    type: string;
    profileImageURL: string;
    verified: boolean;
    id: number;
}

export interface IUserProfileResponse {
    user: IUserProfile;
    message: string;
}


export interface IPostDetail {
    postId: string;
    title: string;
    dateCreated: Date;
    content: string;
    dateModified: Date;
    isArchived: boolean;
    status: string;
    
}

export interface IPostDetailResponse {
    postId: string;
    title: string;
    dateCreated: Date;
    content: string;
    dateModified: Date;
    isArchived: boolean;
    status: string;

    responseStatus: {
        message: string;
        success: boolean;
    }

}



export interface IMessage {
    messageId: number;
    dateCreated: string; // Change type if required
    email: string;
    message: string;
    status: string;
}

export interface IMessageListResponse {
    messagesList: IMessage[];
    message: string;
}

export interface IUpdateMessageStatusResponse {
    success: boolean;
    message: string;
}

export interface IContactAdminRequest {
    subject: string;
    email: string;
    message: string;
}

export interface IContactAdminResponse {
    success: boolean;
    message: string;
}

