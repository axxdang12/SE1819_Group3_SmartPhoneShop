function confirmDelete(element, event) {
        event.preventDefault(); // Ngăn chặn hành động mặc định của liên kết

        // Truy xuất ID sản phẩm từ thuộc tính data-id
        var productId = element.getAttribute('data-id');

        if (confirm('Bạn có chắc chắn muốn xóa sản phẩm này không?')) {
            deleteProduct(productId);
        }
    }
///////
    function deleteProduct(productId) {
           $.ajax({
               url: '/cart/delete-phone/' + productId,
               type: 'POST',
               success: function(response) {


                   // Xóa dòng sản phẩm khỏi bảng
                   $('#' + productId).remove();

                      console.log(response);

                   // Cập nhật tổng giá trị giỏ hàng
                   if(response.cartTotal ==null){
                         $('#totalCart').text('0.00$');
                   }else{
                    $('#totalCart').text(response.cartTotal.toFixed(2) + ' $');

                   }
               },
               error: function(error) {
                   alert('Có lỗi xảy ra khi xóa sản phẩm.');
               }
           });
       }
/////////
function updateQuantity(input, phoneId) {
    // Lấy ID sản phẩm từ thuộc tính data-id
        var phoneId = input.getAttribute('data-id');
        var quantity = input.value;
        var price = parseFloat(input.closest('tr').querySelector('.product-price').getAttribute('data-price'));
   // Kiểm tra giá trị số lượng
      if (input.value < 1 || input.value > 10) {
          alert('Invalid quantity. The quantity must be between 1 and 10.');
          input.value = 1; // Đặt lại giá trị nếu không hợp lệ
          return;
      }

    // Gửi yêu cầu AJAX đến server để cập nhật số lượng
    $.ajax({
        url: '/cart/update-quantity/' + phoneId,
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({ quantity: parseInt(quantity) }),
        success: function(response) {
            // Cập nhật lại tổng giá trị giỏ hàng và các phần tử liên quan trên giao diện

            if(response.cartTotal ==null){
                                     $('#totalCart').text('0.00$');
                               }else{
                                console.log(response.cartTotal);
                                $('#totalCart').text(response.cartTotal.toFixed(2) + ' $');

                               }
            // Cập nhật tổng tiền cho từng sản phẩm
            // Tính toán tổng giá của sản phẩm
                const itemTotal = quantity * price;
                console.log(itemTotal);
                // Cập nhật tổng giá của sản phẩm trong giao diện
                const itemTotalElement = document.querySelector(`td[data-id="${phoneId}"]`);
                console.log(itemTotalElement);
                if (itemTotalElement) {
                    itemTotalElement.textContent = itemTotal.toFixed(2) + ' $';
                }
        },
        error: function(xhr, status, error) {
            console.error('Error updating quantity:', error);
            alert('Failed to update quantity.');
        }
    });
}
