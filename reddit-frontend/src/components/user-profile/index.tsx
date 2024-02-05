import { Card, Avatar, Divider  } from 'antd';
import { EditOutlined, EllipsisOutlined, SettingOutlined } from '@ant-design/icons';
import { Col, Row } from 'antd';

const { Meta } = Card;
type MyComponentProps = {
    className?: string;
};

const UserProfileCard: React.FC<MyComponentProps> = ( {className} ) => {
    return (
        <Card
            className={className}
            actions={[
                <SettingOutlined key="setting" />,
                <EditOutlined key="edit" />,
            ]}
        >   
            <Row align="middle">
                <Col span={8}><Avatar size={200} src="https://api.dicebear.com/7.x/miniavs/svg?seed=8" /></Col>
                <Col span={16}>
                    <Col span={24} style={{fontSize: '40px', fontWeight: '2000px'}}>Jeffrey You</Col>
                    <Divider />
                    <Col span={24}>Joined on January 1, 2020</Col>

                </Col>
            </Row>
                
        </Card>
    )
}

export default UserProfileCard