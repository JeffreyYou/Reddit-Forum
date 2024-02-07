import {create} from "zustand";
import {persist} from "zustand/middleware";
import {IPost} from "../components/post/Posts";

interface IPostStore {
    publishedPosts: IPost[];
    jwtToken: string;

    /*fetchPublishedPosts: () => Promise<IPost[]>;
    fetchDeletedPosts: () => Promise<IPost[]>;
    fetchBannedPosts:()=>Promise<IPost[]>;*/
    /* deletePost: (postid: string) => void;
    recoverPost: (postid: string)=>void;
    banPost:(postid: string)=>void;
    unnbanPost:(postid: string)=>void;*/
}

//const domain = "http://localhost:8085";
/*const publishUrl = `${domain}/posts/published/all`;
const getDeleteUrl = `${domain}/posts/deleted/all`;
const getBannedUrl =`${domain}/posts/banned/all`;*/

/*interface prop {
    name: string;
    bannedPosts: IPost[];
}
export const useNameStore = create<prop>() (
    persist( (set, get) => ({
       name: "123",
        bannedPosts:   [{id: "id1", username: 'User1', date: new Date(2024, 2, 1), title: 'First Post'},
{id: "id2", username: 'User2', date: new Date(2024, 2, 2), title: 'Second Post'},]
    }), {
        name: "test-store",
    })
)*/
export const usePostStore = create<IPostStore>() (
    persist((set, get)=>({
        publishedPosts:  [{id: "id1", username: 'User1', date: new Date(2024, 2, 1), title: 'First Post'},
            {id: "id2", username: 'User2', date: new Date(2024, 2, 2), title: 'Second Post'},],
        jwtToken: "abc"  //get token where
    }), {
            name:"post-store",
    })
)
