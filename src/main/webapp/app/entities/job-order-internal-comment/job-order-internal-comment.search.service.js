(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .factory('JobOrderInternalCommentSearch', JobOrderInternalCommentSearch);

  JobOrderInternalCommentSearch.$inject = ['$resource'];

  function JobOrderInternalCommentSearch($resource) {
    var resourceUrl = 'api/_search/job-order-internal-comments/:id';

    return $resource(resourceUrl, {}, {
      'query': {method: 'GET', isArray: true}
    });
  }
})();
