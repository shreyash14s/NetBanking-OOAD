import { useState } from "react";
import { Form } from "react-router-dom";

export default function AlertModal({ open, setOpen }) {
    return (
        <>
            <input type="checkbox" id="deposit-modal" className="modal-toggle" checked={open} readOnly />
            <div className="modal">
                <div className="modal-box">
                    <h3 className="font-bold text-lg">Pending!</h3>
                    <p className="py-4">Transaction will be pending until approved by admin</p>
                    <div className="modal-action">
                        <button className="btn" onClick={() => setOpen(false)}>Ok</button>
                    </div>
                </div>
            </div>
        </>
    )
}