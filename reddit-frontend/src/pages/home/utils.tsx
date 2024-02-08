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

export const getAllPostItem = (allPosts: IPostDetail[]) => [
  {
    label: <FormOutlined />,
    key: '1',
    children: getCard(allPosts, 'draft'),
    icon: "Drafts"
  }
]
