(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .factory('ApplicantInternalCommentSearch', ApplicantInternalCommentSearch);

  ApplicantInternalCommentSearch.$inject = ['$resource'];

  function ApplicantInternalCommentSearch($resource) {
    var resourceUrl = 'api/_search/applicant-internal-comments/:id';

    return $resource(resourceUrl, {}, {
      'query': {method: 'GET', isArray: true}
    });
  }
})();
