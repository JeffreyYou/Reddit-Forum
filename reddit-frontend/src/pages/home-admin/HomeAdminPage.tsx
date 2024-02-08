import styles from './style.module.scss';
import {Button, Card} from "antd";
import { usePostStore} from "../../store/post-store";
import {useEffect} from "react";


const HomeAdminPage = () => {

    const {publishedPosts, fetchPublishedPosts} = usePostStore();
    const {deletedPosts, fetchDeletedPosts} = usePostStore();
    const {bannedPosts, fetchBannedPosts} = usePostStore();
    const {allUsers, getAllUsers} = usePostStore();
    const {banPost, unbanPost, recoverPost} = usePostStore();

    useEffect(() => {
        fetchPublishedPosts();
        fetchBannedPosts();
        fetchDeletedPosts();
        getAllUsers();

    },[]);

    const handleBan = (postid:string)=>{
        banPost(postid);
    };

    const handleUnban = (postid: string)=>{
        unbanPost(postid);
    }
    const handleRecover = (postid: string)=>{
        recoverPost(postid);
    }

    const getUsername = (userid: number)=>{
      return allUsers.filter(x=>x.id===userid).map(x=>x.firstName+" "+x.lastName);
    }


    return ( //
        <div className={styles.homepage_container}>
            <div>
                <h1>Hello! This is your Admin homepage.</h1>
            </div>

            <div className={styles.content}>
                {publishedPosts.map((post) => (
                    <div key={post.postId}  >
                    <Card
                        className="reddit-card"
                        hoverable
                    >
                        <h3 className="reddit-card-title">{post.title}</h3>
                        <p className="reddit-card-meta">{`${getUsername(post.userId)}- ${post.dateCreated}`}</p>
                        <Button type="primary" onClick={()=>handleBan(post.postId)}> Ban this post </Button>
                    </Card>

                    </div>
                ))}
            </div>

            <div  className={styles.content}>
                <h1> Deleted posts</h1>
                {deletedPosts.map((post) => (
                        <Card
                            key={post.postId}
                            className="reddit-card"
                            hoverable
                        >
                            <h3 className="reddit-card-title">{post.title}</h3>
                            <p className="reddit-card-meta">{`${getUsername(post.userId)} - ${post.dateCreated}`}</p>
                            <Button type="primary" onClick={()=>handleRecover(post.postId)}> Recover this post </Button>
                        </Card>
                ))}
            </div>

            <div  className={styles.content}>
                <h1> Banned posts</h1>
                {bannedPosts.map((post) => (
                    <Card
                        key={post.postId}
                        className="reddit-card"
                        hoverable
                    >
                        <h3 className="reddit-card-title">{post.title}</h3>
                        <p className="reddit-card-meta">{`${getUsername(post.userId)} - ${post.dateCreated}`}</p>
                        <Button type="primary" onClick={()=>handleUnban(post.postId)}> Unban this post </Button>
                    </Card>
                ))}
            </div>

        </div>
    );
};

export default HomeAdminPage;
