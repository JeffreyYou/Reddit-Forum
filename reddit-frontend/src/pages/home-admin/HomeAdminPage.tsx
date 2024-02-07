import styles from './style.module.scss';
import {Button, Card} from "antd";
import { usePostStore} from "../../store/post-store";

const HomeAdminPage = () => {

    const {publishedPosts} = usePostStore();

    console.log(publishedPosts);
    const deletedPosts = publishedPosts;
    const bannedPosts = publishedPosts;
    /*useEffect(() => {
        //fetchPublishedPosts(); // fetch posts for every render
        //console.log(publishedPosts);

        //fetchBannedPosts();
        //fetchDeletedPosts();
    });*/

    const banPost = (postid:string)=>(
        console.log(postid)
    );

    const deletePost = (postid: string)=>{
        console.log(postid)
    }


    return ( //
        <div className={styles.homepage_container}>
            <div>
                <h1>Hello! This is your Admin homepage.</h1>
            </div>

            <div className={styles.posts_container}>
                {publishedPosts.map((post) => (
                    <div>
                    <Card
                        key={post.id}
                        className="reddit-card"
                        hoverable
                    >
                        <h3 className="reddit-card-title">{post.title}</h3>
                        <p className="reddit-card-meta">{`${post.username} - ${post.date}`}</p>

                    </Card>
                    <Button type="primary" onClick={()=>banPost(post.id)}> Ban this post </Button>
                    <Button type="primary" onClick={()=>deletePost(post.id)}> Delete this post </Button>
                    </div>
                ))}
            </div>

            <div className={styles.posts_container}>
                <h3> Deleted posts</h3>
                {deletedPosts.map((post) => (
                        <Card
                            key={post.id}
                            className="reddit-card"
                            hoverable
                        >
                            <h3 className="reddit-card-title">{post.title}</h3>
                            <p className="reddit-card-meta">{`${post.username} - ${post.date}`}</p>
                            <Button type="primary"> Recover this post </Button>
                        </Card>
                ))}
            </div>

            <div className={styles.posts_container}>
                <h3> Banned posts</h3>
                {bannedPosts.map((post) => (
                    <Card
                        key={post.id}
                        className="reddit-card"
                        hoverable
                    >
                        <h3 className="reddit-card-title">{post.title}</h3>
                        <p className="reddit-card-meta">{`${post.username} - ${post.date}`}</p>
                        <Button type="primary"> Unban this post </Button>
                    </Card>
                ))}
            </div>

        </div>
    );
};

export default HomeAdminPage;
