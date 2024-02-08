import PostOverviewCard from '../../components/post-overview-card';
import { IPostDetail } from '../../store/interface';
import { FundProjectionScreenOutlined, FormOutlined, FireOutlined, WarningOutlined } from '@ant-design/icons';

export const getCard = (draftPosts: IPostDetail[], type:string) => {
    return (
      <>
        {
          draftPosts.map((post: IPostDetail) => {
            return (
              <PostOverviewCard post={post} key={post.postId} type={type} />
            )
          })
        }
      </>
    )
}

export const getDraftAndHistoryPostsItem = (draftPosts: IPostDetail[], historyPosts: IPostDetail[]) => [
  {
    label: <FormOutlined />,
    key: '1',
    children: getCard(draftPosts, 'draft'),
    icon: "Drafts"
  },
  {
    label: <FundProjectionScreenOutlined />,
    key: '2',
    children: getCard(historyPosts, 'history'),
    icon: 'History'
  },
]
export const getTop3PostsItem = (top3Posts: IPostDetail[]) => [
  {
    label: <FireOutlined style={{ color: 'red' }} />,
    key: '1',
    children: getCard(top3Posts, 'posts'),
    icon: "Top 3 Posts"
  }
]