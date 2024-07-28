import React, { useState } from 'react';
import { BrowserRouter as Router, Route, Routes, Link } from 'react-router-dom';
import AuctionItemList from './components/AuctionItemList';
import AuctionItemDetail from './components/AuctionItemDetail';
import AuctionItemEdit from './components/AuctionItemEdit'; // Updated import
import { Container, Row, Col } from 'react-bootstrap';

const App = () => {
    const [selectedItemId, setSelectedItemId] = useState(null);

    return (
        <Router>
            <Container>
                <h1 className="my-4">Auction App</h1>
                <Row>
                    <Col md={4}>
                        <AuctionItemList onSelectItem={setSelectedItemId} />
                    </Col>
                    <Col md={8}>
                        <Routes>
                            <Route path="/edit/:itemId" element={<AuctionItemEdit />} /> {/* Updated route */}
                            <Route path="/" element={<AuctionItemDetail itemId={selectedItemId} />} />
                        </Routes>
                    </Col>
                </Row>
            </Container>
        </Router>
    );
};

export default App;
