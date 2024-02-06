import { Card, Avatar, Divider  } from 'antd';
import { EditOutlined, EllipsisOutlined, SettingOutlined } from '@ant-design/icons';
import { Col, Row } from 'antd';

import { IUserProfile } from '../../store/interface';
type MyComponentProps = {
    className?: string;
    user: IUserProfile;
};

import GirlSvg1 from '../../assets/girl/girl1.svg'
import GirlSvg2 from '../../assets/girl/girl2.svg'
import GirlSvg3 from '../../assets/girl/girl3.svg'
import GirlSvg4 from '../../assets/girl/girl4.svg'
import GirlSvg5 from '../../assets/girl/girl5.svg'
const testSvgUrl = "https://api.dicebear.com/7.x/miniavs/svg?seed=8";

const UserProfileCard: React.FC<MyComponentProps> = ( {className, user} ) => {
    return (
        <Card
            className={className}
            actions={[
                <SettingOutlined key="setting" />,
                <EditOutlined key="edit" />,
            ]}
        >   
            <Row align="middle">
                <Col span={8}><Avatar size={200} src={GirlSvg4} /></Col>
                <Col span={16}>
                    <Col span={24} style={{fontSize: '40px'}}>{user.firstName} {user.lastName}</Col>
                    <Divider />
                    <Col span={24}>{user.dateJoined}</Col>

                </Col>
            </Row>
                
        </Card>
    )
}

export default UserProfileCard